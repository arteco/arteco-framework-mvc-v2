package com.arteco.mvc.jetty;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebContainer;
import com.arteco.mvc.bootstrap.WebMvc;
import com.arteco.mvc.bootstrap.WebMvcServlet;
import com.arteco.mvc.bootstrap.servlet.FilterDefinition;
import com.arteco.mvc.bootstrap.servlet.ServletDefinition;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Map;

public class JettyContainer implements WebContainer {

    private String contextPath;
    private WebAppContext webapp;
    private Server server;
    private WebApp app;


    @Override
    public void init(WebApp app) {
        this.app = app;

        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(app.getPort());
        server.setConnectors(new Connector[]{connector});

        this.contextPath = StringUtils.defaultIfBlank(app.getContextPath(), "/");

        webapp = new WebAppContext();
        webapp.setContextPath(contextPath);
        webapp.setResourceBase(".");
        server.setHandler(webapp);

    }


    @Override
    public void registerServlets() {
        app.getServlets().forEach(sd -> {
            int i = 0;
            String[] mapp = sd.getUrlMappings();
            for (String m : mapp) {
                webapp.addServlet(getServletInfo(sd, i++), m);
            }
        });
    }

    private ServletHolder getServletInfo(ServletDefinition sd, int i) {
        ServletHolder holder = new ServletHolder(sd.getServlet());
        holder.setName(sd.getName() + (i > 0 ? "_" + i : ""));
        holder.setInitParameters(sd.getInitParameters());
        return holder;
    }

    @Override
    public void registerFilters() {
        app.getFilters().forEach(sd -> {
            int i = 0;
            String[] mapp = sd.getUrlMappings();
            for (String m : mapp) {
                webapp.addFilter(getFilterInfo(sd, i++), m,
                        EnumSet.of(DispatcherType.REQUEST));
            }
        });
    }

    private FilterHolder getFilterInfo(FilterDefinition sd, int i) {
        FilterHolder holder = new FilterHolder(sd.getFilter());
        holder.setName(sd.getName() + (i > 0 ? "_" + i : ""));
        Map<String, String> params = sd.getInitParameters();
        holder.setInitParameters(params);
        return holder;
    }


    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }

    @Override
    public void registerDefaultServlet() {
        ServletHolder holder = new ServletHolder(new WebMvcServlet(app));
        holder.setName(WebMvcServlet.NAME);
        webapp.addServlet(holder, WebMvc.MAPPING);
    }
}
