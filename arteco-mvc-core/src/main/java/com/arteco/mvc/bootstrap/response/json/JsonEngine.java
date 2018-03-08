package com.arteco.mvc.bootstrap.response.json;

import com.arteco.mvc.bootstrap.error.exception.WebParseException;

import java.io.InputStream;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface JsonEngine {
    String toJson(Object object);

    <T> T fromJson(InputStream inputStream, Class<T> type) throws WebParseException;
}
