package com.arteco.mvc.sample;


import com.arteco.mvc.ajaxui.AjaxUIWebModule;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebAppRegister;
import com.arteco.mvc.bootstrap.database.DatabaseJdbcProcessor;
import com.arteco.mvc.closurejs.ClosureJsWebModule;
import com.arteco.mvc.jetty.JettyContainer;
import com.arteco.mvc.sample.controller.AjaxUIController;
import com.arteco.mvc.sample.controller.IndexController;
import com.arteco.mvc.sass.SassWebModule;
import com.arteco.mvc.swagger.SwaggerWebModule;
import com.arteco.mvc.swagger.v2.SwaggerV2Factory;
import com.arteco.mvc.swagger.v3.SwaggerV3Factory;
//import com.arteco.mvc.vaadin.VaadinWebModule;

/**
 * Created by rarnau on 10/11/16.
 * Arteco Consulting Sl.
 * mailto: info@arteco-consulting.com
 */
public class SampleApp implements WebAppRegister {


    public static final String JDBC_URL = "jdbc:mysql://localhost/myapp?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useSSL=false";

    @Override
    public void configure(WebApp app) {
        app.setContainer(new JettyContainer());
        app.addController(new IndexController());
        app.addController(new AjaxUIController());

        app.addModule(new SwaggerWebModule(new SwaggerV2Factory()));
        app.addModule(new SwaggerWebModule(new SwaggerV3Factory()));
        app.addModule(new SassWebModule());
        app.addModule(new ClosureJsWebModule());
        app.addModule(new AjaxUIWebModule());
//        app.addModule(new VaadinWebModule(SampleUI.class, "/ui/*"));
        app.setDatabase(new DatabaseJdbcProcessor(JDBC_URL, "root", ""));
    }
}
