package com.arteco.mvc.bootstrap.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class ExceptionUtils {
    public static String toString(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter prt = new PrintWriter(baos);
        e.printStackTrace(prt);
        prt.close();
        return new String(baos.toByteArray());
    }

    public static RuntimeException manage(Exception e) {
        return new RuntimeException(e.getMessage(), e);
    }

    public static RuntimeException trigger(String message) {
        return new RuntimeException(message);
    }
}
