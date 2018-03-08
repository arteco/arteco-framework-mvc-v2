package com.arteco.mvc.undertow;

import com.arteco.mvc.bootstrap.RunnableWebApp;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebContainer;
import com.arteco.mvc.bootstrap.WebMvcServlet;
import com.arteco.mvc.bootstrap.servlet.FilterDefinition;
import com.arteco.mvc.bootstrap.servlet.ServletDefinition;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.util.ImmediateInstanceHandle;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.Servlet;

public class UndertowContainer implements WebContainer {

    private WebApp app;
    private DeploymentInfo servletBuilder;
    private Undertow server;
    private String contextPath;


    private ServletInfo getServletInfo(ServletDefinition sd) {
        Servlet servlet = sd.getServlet();
        ServletInfo si = new ServletInfo(
                sd.getName(),
                servlet.getClass(),
                () -> new ImmediateInstanceHandle<>(servlet));
        sd.getInitParameters().forEach(si::addInitParam);
        si.addMappings(sd.getUrlMappings());
        return si;
    }

    private static ServletInfo getDefaultServlet(WebApp app) {
        return new ServletInfo(WebMvcServlet.NAME,
                WebMvcServlet.class,
                () -> new ImmediateInstanceHandle<>(new WebMvcServlet(app)));
    }

    @Override
    public void init(WebApp app) {
        this.app = app;
        this.contextPath = StringUtils.defaultIfBlank(app.getContextPath(), "/");
        this.servletBuilder = Servlets.deployment()
                .setClassLoader(RunnableWebApp.class.getClassLoader())
                .setContextPath(contextPath)
                .setDeploymentName(app.getInfo().getName());
    }

    @Override
    public void registerServlets() {
        app.getServlets().forEach(sd ->
                servletBuilder.addServlet(getServletInfo(sd))
        );
    }

    @Override
    public void registerFilters() {
        app.getFilters().forEach(sd ->
                servletBuilder.addFilter(getFilterInfo(sd))
        );
    }

    private FilterInfo getFilterInfo(FilterDefinition sd) {
        Filter filter = sd.getFilter();
        return new FilterInfo(sd.getName(),
                filter.getClass(),
                () -> new ImmediateInstanceHandle<>(filter));
    }

    @Override
    public void start() {
        try {
            DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            PathHandler path = Handlers.path(Handlers.redirect(contextPath))
                    .addPrefixPath(contextPath, manager.start());

            this.server = Undertow.builder()
                    .addHttpListener(app.getPort(), app.getHost())
                    .setHandler(path)
                    .build();

            server.start();
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }

    @Override
    public void registerDefaultServlet() {
        this.servletBuilder.addServlet(getDefaultServlet(app));
    }
}
