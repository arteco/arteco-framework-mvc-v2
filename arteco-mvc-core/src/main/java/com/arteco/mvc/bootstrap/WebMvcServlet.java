package com.arteco.mvc.bootstrap;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class WebMvcServlet extends WebMvc implements Servlet {

    public static final String NAME = "artecoMainServlet";

    private ServletConfig servletConfig;

    public WebMvcServlet(WebApp application) {
        super(application);
    }

    @Override
    public void init(ServletConfig config) {
        this.servletConfig = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        super.serve(req, res);
    }

    @Override
    public String getServletInfo() {
        return "Arteco MVC Servlet";
    }
}
