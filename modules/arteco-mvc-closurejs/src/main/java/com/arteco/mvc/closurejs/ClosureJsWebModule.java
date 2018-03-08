package com.arteco.mvc.closurejs;


import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebModule;
import com.arteco.mvc.bootstrap.resource.WebResourceLoader;

@SuppressWarnings("unused")
public class ClosureJsWebModule implements WebModule {


    public static ClosureJsResponse closureJs(WebResourceLoader resourceLoader) {
        return new ClosureJsResponse(resourceLoader);
    }

    @Override
    public void configure(WebApp webApp) {
    }
}
