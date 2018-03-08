package com.arteco.mvc.bootstrap.converter.impl;

import com.arteco.mvc.bootstrap.converter.Converter;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class StringToIntConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }

}
