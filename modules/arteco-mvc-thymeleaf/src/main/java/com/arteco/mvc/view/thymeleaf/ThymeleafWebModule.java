package com.arteco.mvc.view.thymeleaf;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebModule;

@SuppressWarnings("unused")
public class ThymeleafWebModule implements WebModule {
    @Override
    public void configure(WebApp webApp) {
        webApp.viewEngine(new ThymeleafViewEngine(webApp));
    }
}
