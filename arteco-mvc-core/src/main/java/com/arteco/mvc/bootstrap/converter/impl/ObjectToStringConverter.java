package com.arteco.mvc.bootstrap.converter.impl;

import com.arteco.mvc.bootstrap.converter.Converter;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class ObjectToStringConverter implements Converter<Object, String> {
    @Override
    public String convert(Object source) {
        return source.toString();
    }


}
