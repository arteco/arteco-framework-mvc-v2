package com.arteco.mvc.ajaxui;

import com.arteco.mvc.ajaxui.interceptor.IsNewFilterDef;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebModule;

/**
 * Created by rarnau on 17/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class AjaxUIWebModule implements WebModule {

    private final String urlPrefix;
    private final String homePage;

    public AjaxUIWebModule(String urlPrefix, String homePage) {
        this.urlPrefix = urlPrefix;
        this.homePage = homePage;
    }

    public AjaxUIWebModule() {
        this("/ajaxui", "/home");
    }

    @Override
    public void configure(WebApp webApp) {
        webApp.addController(new AjaxUiController(webApp, urlPrefix, homePage));
        webApp.addFilter(new IsNewFilterDef(webApp));
    }
}
