package com.arteco.mvc.bootstrap.converter;

import com.arteco.mvc.bootstrap.converter.impl.ObjectToStringConverter;
import com.arteco.mvc.bootstrap.converter.impl.StringToIntConverter;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.bootstrap.utils.ObjectUtils;
import lombok.Data;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class Converters implements WebConversion {

    private static final Converters instance = new Converters();

    private Map<ConverterPair, Converter> cache = new HashMap<>();
    private List<Converter> converters = new ArrayList<>();

    private Converter identityConverter = source -> source;

    private Converters() {
        converters.add(new StringToIntConverter());
        converters.add(new ObjectToStringConverter());
    }

    public static WebConversion getInstance() {
        return instance;
    }

    public void register(Converter converter) {
        converters.add(converter);
    }


    public <T> T convert(Object value, Class<T> targetType) {
        if (value != null) {
            Class sourceType = value.getClass();
            ConverterPair pair = new ConverterPair(sourceType, targetType);
            Converter converter = cache.get(pair);
            if (converter == null) {
                converter = lookupConverter(pair);
                if (converter != null) {
                    cache.put(pair, converter);
                } else {
                    throw ExceptionUtils.trigger("No suitable converter for " + targetType + " from " + sourceType);
                }
            }
            if (sourceType.isArray()) {
                if (targetType.isArray()) {
                    // se debe convertir uno por uno
                    Object[] values = (Object[]) value;
                    T result = (T) Array.newInstance(targetType, values.length);
                    int i = 0;
                    for (Object v : values) {
                        v = converter.convert(v);
                        Arrays.fill((Object[]) result, i++, i, v);

                    }
                    return result;
                } else {
                    // nos quedamos el último
                    Object[] result = (Object[]) value;
                    if (result.length > 0) {
                        return (T) converter.convert(result[result.length - 1]);
                    }
                }
            } else {
                if (targetType.isArray()) {
                    // generamos un array de un elemento
                    value = converter.convert(value);
                    T result = (T) Array.newInstance(targetType, 1);
                    Arrays.fill((Object[]) result, value);
                    return result;
                } else {
                    // 1 a 1
                    return (T) converter.convert(value);
                }
            }
        }
        return null;
    }

    private Converter lookupConverter(ConverterPair pair) {
        if (pair.sourceType.equals(pair.targetType)) {
            return identityConverter;
        }
        for (Converter converter : converters) {
            Class<?>[] types = ObjectUtils.resolveRawArguments(Converter.class, converter.getClass());
            if (assignable(pair.sourceType, types[0]) && assignable(pair.targetType, types[1])) {
                return converter;
            }
        }
        return null;
    }

    private boolean assignable(Class actualType, Class converterType) {
        try {
            return converterType.isAssignableFrom(actualType);
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }


    @Data
    private class ConverterPair {
        Class<?> sourceType;
        Class<?> targetType;

        ConverterPair(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType.isPrimitive() ? ClassUtils.primitiveToWrapper(sourceType) : sourceType;
            this.targetType = targetType.isPrimitive() ? ClassUtils.primitiveToWrapper(targetType) : targetType;
        }
    }
}
