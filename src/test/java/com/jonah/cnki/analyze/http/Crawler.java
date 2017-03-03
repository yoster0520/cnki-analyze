package com.jonah.cnki.analyze.http;

import com.jonah.cnki.analyze.CnkiAnalyzeApplicationTests;
import com.jonah.cnki.analyze.model.NoteTableContent;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jonah
 * @since 2017/3/3-11:49
 */
public class Crawler extends CnkiAnalyzeApplicationTests {
    public static final Logger LOGGER =
            LoggerFactory.getLogger(Crawler.class);
    private String path = "";
    private CloseableHttpClient httpclient;

    @Before
    public void setUp() throws IOException {
        httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(
                "http://kns.cnki.net/kns/request/SearchHandler.ashx?action&NaviCode=*&ua=1.11&PageName=ASP.brief_default_result_aspx&DbPrefix=SCDB&DbCatalog=%E4%B8%AD%E5%9B%BD%E5%AD%A6%E6%9C%AF%E6%96%87%E7%8C%AE%E7%BD%91%E7%BB%9C%E5%87%BA%E7%89%88%E6%80%BB%E5%BA%93&ConfigFile=SCDBINDEX.xml&db_opt=CJFQ,CDFD,CMFD,CPFD,IPFD,CCND&txt_1_sel=SU$%25%3D%7C&txt_1_value1=%E7%88%AC%E8%99%AB%E7%B3%BB%E7%BB%9F&txt_1_special1=%25&his=0&parentdb=SCDB&__=Fri%20Mar%2003%202017%2010:57:34%20GMT%2B0800%20(CST)");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            //如果正确执行而且返回值正确，即可解析
            if (response != null
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();

                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(entity.getContent()));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    path += line;
                }
            }
        } finally {
            response.close();
        }
    }

    @Test
    public void getContent() throws IOException {
        String url = "http://kns.cnki.net/kns/brief/brief.aspx?pagename=" + path;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            //如果正确执行而且返回值正确，即可解析
            if (response != null
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();

                entity.getContent();
                Document document = Jsoup.parse(entity.getContent(), "UTF-8",
                        "http://kns.cnki.net/kns/request/SearchHandler.ashx");
                Elements elements = document.select(".GridTableContent");
                Elements title = elements.select(".GTContentTitle");
                Elements contents = elements.select("TR[bgcolor]");
                List<NoteTableContent> noteTableContents = new ArrayList<>();
                for (Element content : contents) {
                    Elements td = content.select("td");
                    NoteTableContent noteTableContent = new NoteTableContent();
                    for (int i = 0; i < td.size(); i++) {
                        String data = td.get(i).data();
                        switch (i) {
                            case 0:
                                data = td.get(i).childNode(1).toString();
                                noteTableContent.setId(Integer.parseInt(data));
                                break;
                            case 1:
                                StringBuilder result = new StringBuilder();
                                Element a = td.get(i).select(".fz14").get(0);
                                noteTableContent.setDetailUrl(a.attr("href"));
                                a.childNodes()
                                        .forEach(node -> {
                                                    result.append(node.toString());
                                                }
                                        );
                                data = result.toString();
                                noteTableContent.setMarkTitle(data);
                                data = data.replace("<font class=\"Mark\">", "");
                                data = data.replace("</font>", "");
                                noteTableContent.setTitle(data);
                                break;
                            case 2:
                                for (Node knowledgeNetLink : td.get(i).childNodes()) {
                                    if (StringUtils.isEmpty(knowledgeNetLink.toString().trim())
                                            || knowledgeNetLink.toString().trim().equals(";")) continue;
                                    data += knowledgeNetLink.childNode(0).toString() + ";";
                                }
                                noteTableContent.setAuthor(data);
                                break;
                            case 3:
                                data = td.get(i).childNode(1).childNode(0).toString();
                                noteTableContent.setFrom(data);
                                break;
                            case 4:
                                data = td.get(i).childNode(0).toString();
                                noteTableContent.setDate(data);
                                break;
                            case 5:
                                data = td.get(i).childNode(0).toString();
                                noteTableContent.setDatabase(data.trim());
                                break;
                            case 6:
                                if (data.equals("</span>")) break;
                                if (!td.get(i).select("a").isEmpty())
                                    data = td.get(i).select("a").get(0).childNode(0).toString();
                                if (StringUtils.isEmpty(data)) data = "0";
                                noteTableContent.setKnowledgeNetCount(Integer.parseInt(data));
                                break;
                            case 7:
                                data = td.get(i).childNode(3).childNode(0).childNode(0).toString();
                                if (StringUtils.isEmpty(data)) data = "0";
                                noteTableContent.setDownloadCount(Integer.parseInt(data));
                                break;
                            case 9:
                                data = td.get(i).childNode(1).childNode(0).childNode(0).toString();
                                if (StringUtils.isEmpty(data)) data = "0.0";
                                noteTableContent.setHotSpot(Double.parseDouble(data));
                                break;
                            default:
                                break;
                        }
                    }
                    noteTableContents.add(noteTableContent);
                }
                LOGGER.info(noteTableContents.toString());
            }
        } finally {
            response.close();
        }
    }

    @After
    public void tearDown() throws IOException {
        if (httpclient != null) {
            httpclient.close();
        }
    }
}
