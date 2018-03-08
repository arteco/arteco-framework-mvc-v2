package com.arteco.mvc.vaadin;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebModule;
import com.arteco.mvc.bootstrap.servlet.ServletDefinition;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

public class VaadinWebModule implements WebModule {

    public static final String DEFAULT_MAP = "/ui/*";
    private final Class<? extends UI> uiClass;
    private final String path;
    private final String vaadin;

    public VaadinWebModule(Class<? extends UI> uiClass) {
        this(uiClass, null);
    }

    public VaadinWebModule(Class<? extends UI> uiClass, String path) {
        this.uiClass = uiClass;
        this.path = StringUtils.defaultIfBlank(path, DEFAULT_MAP);
        this.vaadin = "/VAADIN/*";
    }

    @Override
    public void configure(WebApp webApp) {
        webApp.addServlet(new ServletDefinition() {

            @Override
            public String getName() {
                return "vaadinServlet";
            }

            @Override
            public Map<String, String> getInitParameters() {
                Map<String, String> result = new HashMap<>();
                result.put("UI", uiClass.getName());
                return result;
            }

            @Override
            public String[] getUrlMappings() {
//                if (DEFAULT_MAP.equals(path)) {
//                    return new String[]{path};
//                }
                return new String[]{path, vaadin};
            }

            @Override
            public Servlet getServlet() {
                return new VaadinServlet();
            }
        });
    }
}
