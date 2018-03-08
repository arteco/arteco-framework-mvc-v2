package com.arteco.mvc.view.jade;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebModule;

@SuppressWarnings("unused")
public class JadeWebModule implements WebModule {
    @Override
    public void configure(WebApp webApp) {
        webApp.viewEngine(new JadeViewEngine());
    }
}
