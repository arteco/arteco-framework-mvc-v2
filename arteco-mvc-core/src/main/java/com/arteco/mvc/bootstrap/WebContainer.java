package com.arteco.mvc.bootstrap;

public interface WebContainer {
    void init(WebApp webApp);

    void registerServlets();

    void registerFilters();

    void start();

    void registerDefaultServlet();
}
