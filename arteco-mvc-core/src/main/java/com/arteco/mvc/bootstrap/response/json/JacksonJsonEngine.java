package com.arteco.mvc.bootstrap.response.json;

import com.arteco.mvc.bootstrap.error.exception.WebParseException;
import com.arteco.mvc.bootstrap.response.yaml.YamlEngine;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class JacksonJsonEngine implements JsonEngine, YamlEngine {

    private final ObjectMapper jsonObjectMapper;
    private final ObjectMapper yamlObjectMapper = new ObjectMapper(new YAMLFactory());

    public JacksonJsonEngine() {
        this.jsonObjectMapper = new ObjectMapper();

        this.jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.yamlObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    public JacksonJsonEngine(ObjectMapper objectMapper) {
        this.jsonObjectMapper = objectMapper;

    }

    @Override
    public String toJson(Object object) {
        try {
            return jsonObjectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    @Override
    public <T> T fromJson(InputStream inputStream, Class<T> type) throws WebParseException {
        try {
            return jsonObjectMapper.readValue(inputStream, type);
        } catch (Exception e) {
            throw new WebParseException(e.getMessage(), e);
        }
    }

    @Override
    public String toYaml(Object object) {
        try {
            return yamlObjectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    @Override
    public <T> T fromYaml(InputStream inputStream, Class<T> type) throws WebParseException {
        try {
            return yamlObjectMapper.readValue(inputStream, type);
        } catch (Exception e) {
            throw new WebParseException(e.getMessage(), e);
        }
    }
}
