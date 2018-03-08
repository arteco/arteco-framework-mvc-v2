package com.arteco.mvc.bootstrap;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class WebThread {

    private static final ThreadLocal<WebContext> contexts = new ThreadLocal<>();

    public static WebContext get() {
        return contexts.get();
    }

    public static void set(WebContext webContext) {
        contexts.set(webContext);
    }
}
