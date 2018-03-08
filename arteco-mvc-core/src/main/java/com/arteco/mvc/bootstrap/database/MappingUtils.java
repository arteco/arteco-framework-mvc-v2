package com.arteco.mvc.bootstrap.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class MappingUtils {
    public static <T, K> List<T> map(Collection<K> collection, Function<K, T> mapper) {
        List<T> result = new ArrayList<>();
        if (collection != null) {
            for(K k : collection){
                result.add(mapper.apply(k));
            }
        }
        return result;
    }
}
