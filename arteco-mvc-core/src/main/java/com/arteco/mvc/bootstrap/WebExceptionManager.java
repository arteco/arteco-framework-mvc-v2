package com.arteco.mvc.bootstrap;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebExceptionManager {
    void manage(WebContext webContext, Exception e);
}
