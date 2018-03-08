package com.arteco.mvc.bootstrap.converter;

public interface WebConversion {
    <T> T convert(Object value, Class<T> targetType);

    void register(Converter converter);
}
