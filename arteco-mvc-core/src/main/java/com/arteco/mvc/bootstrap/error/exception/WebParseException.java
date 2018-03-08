package com.arteco.mvc.bootstrap.error.exception;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class WebParseException extends Exception {
    public WebParseException(String message, Exception e) {
        super(message, e);
    }
}
