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

    @Override
    public void configure(WebApp webApp) {
        webApp.addFilter(new IsNewFilterDef(webApp));
    }
}
