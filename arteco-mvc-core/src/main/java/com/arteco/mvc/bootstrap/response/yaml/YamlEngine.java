package com.arteco.mvc.bootstrap.response.yaml;

import com.arteco.mvc.bootstrap.error.exception.WebParseException;

import java.io.InputStream;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface YamlEngine {
    String toYaml(Object object);

    <T> T fromYaml(InputStream inputStream, Class<T> type) throws WebParseException;
}
