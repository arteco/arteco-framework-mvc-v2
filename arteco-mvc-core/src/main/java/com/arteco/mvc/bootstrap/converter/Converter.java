package com.arteco.mvc.bootstrap.converter;

import lombok.NonNull;

/**
 * Developed by Arteco Consulting Sl.
 * Author rarnau on 11/11/16.
 */
public interface Converter<SRC, TRG> {

    TRG convert(@NonNull SRC source);

}
