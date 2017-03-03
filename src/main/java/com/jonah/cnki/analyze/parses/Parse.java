package com.jonah.cnki.analyze.parses;

import com.jonah.cnki.analyze.common.exceptions.BaseException;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * common parse interface.
 *
 * @author jonah
 * @since 2017/3/3-17:38
 */
public interface Parse<T> {
    T parse(CloseableHttpResponse response) throws BaseException;
}
