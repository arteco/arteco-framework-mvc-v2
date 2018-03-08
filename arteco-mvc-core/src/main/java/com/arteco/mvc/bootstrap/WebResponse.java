package com.arteco.mvc.bootstrap;

/**
 * Created by rarnau on 24/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebResponse<T> {

    void process(WebContext webContext);

    //FIXME: es necesario este método?
    default T getValue() {
        return null;
    }
}
