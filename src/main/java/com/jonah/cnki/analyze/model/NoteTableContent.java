package com.jonah.cnki.analyze.model;

/**
 * @author jonah
 * @since 2017/3/3-15:26
 */
public class NoteTableContent {
    private int id;
    private String title;
    private String author;
    private String from;
    private String date;
    private String database;
    private int knowledgeNetCount;
    private int downloadCount;
    private double hotSpot;
    private String markTitle;
    private String detailUrl;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getKnowledgeNetCount() {
        return knowledgeNetCount;
    }

    public void setKnowledgeNetCount(int knowledgeNetCount) {
        this.knowledgeNetCount = knowledgeNetCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public double getHotSpot() {
        return hotSpot;
    }

    public void setHotSpot(double hotSpot) {
        this.hotSpot = hotSpot;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getMarkTitle() {
        return markTitle;
    }

    public void setMarkTitle(String markTitle) {
        this.markTitle = markTitle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Override
    public String toString() {
        return "NoteTableContent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", from='" + from + '\'' +
                ", date='" + date + '\'' +
                ", database='" + database + '\'' +
                ", knowledgeNetCount=" + knowledgeNetCount +
                ", downloadCount=" + downloadCount +
                ", hotSpot=" + hotSpot +
                '}';
    }
}
