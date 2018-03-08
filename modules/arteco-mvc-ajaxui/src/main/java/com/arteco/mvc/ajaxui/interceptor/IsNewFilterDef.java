package com.arteco.mvc.ajaxui.interceptor;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.servlet.FilterDefinition;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarnau on 5/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class IsNewFilterDef implements FilterDefinition {
    private final WebApp app;

    public IsNewFilterDef(WebApp app) {
        this.app = app;
    }

    @Override
    public Filter getFilter() {
        return new AjaxIsNewFilter(app);
    }

    @Override
    public String getName() {
        return "Ajaxui Arteco MVC";
    }

    @Override
    public String[] getUrlMappings() {
        return new String[]{"/*"};
    }

    @Override
    public Map<String, String> getInitParameters() {
        return new HashMap<>();
    }
}
