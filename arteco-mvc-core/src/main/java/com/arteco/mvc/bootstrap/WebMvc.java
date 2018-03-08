package com.arteco.mvc.bootstrap;

import com.arteco.mvc.bootstrap.chain.WebChainRunner;
import com.arteco.mvc.bootstrap.router.WebMethod;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.NonNull;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebMvc {

    public static final String MAPPING = "/*";

    private final WebApp application;

    public WebMvc(WebApp application) {
        this.application = application;
    }

    public static WebContext getOrCreateContext(ServletRequest request, ServletResponse response, WebApp application) {
        WebContext webContext = WebThread.get();
        if (webContext == null) {
            webContext = new WebServletContext(
                    application,
                    (HttpServletRequest) request,
                    (HttpServletResponse) response);
            WebThread.set(webContext);
        }
        return webContext;
    }

    @NonNull
    private WebRoute getRoute(WebContext webContext) {
        String uri = webContext.getURI();
        WebMethod verb = webContext.getVerb();
        WebApp app = webContext.getApp();
        WebRoute route = app.getRegister().lookup(webContext, verb, uri);
        if (route == null) {
            //TODO: check if static resource
            route = new WebRoute<>((from) -> {
                try {
                    application.getNotFound().process(webContext, null);
                } catch (Exception e) {
                    throw ExceptionUtils.manage(e);
                }
                return null;
            });
        }
        webContext.setWebRoute(route);
        return route;
    }

    private WebChainRunner getChainRunner(WebRoute route) {
        WebChainRunner chainRunner = route.getChain();
        if (chainRunner == null) {
            chainRunner = application.getChainRunner();
        }
        return chainRunner;
    }

    public void destroy() {
        //do nothing
    }

    public void serve(ServletRequest request, ServletResponse response) {
        WebContext webContext = WebMvc.getOrCreateContext(request, response, application);
        WebRoute route = getRoute(webContext);
        WebChainRunner chainRunner = getChainRunner(route);
        chainRunner.process(webContext);
    }
}
