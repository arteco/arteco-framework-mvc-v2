package com.arteco.mvc.bootstrap.response.view;

import com.arteco.mvc.bootstrap.WebContext;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebViewEngine {
    void process(WebContext webContext, ViewWebResponse viewWebResponse);

}
