package com.arteco.mvc.bootstrap.router;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rarnau on 24/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
@Slf4j
public class RouterRegister {

    private final WebApp webApp;
    private final List<WebRoute> routes = new ArrayList<>();

    public RouterRegister(WebApp webApp) {
        this.webApp = webApp;
    }


    private void addRoutePrivate(WebRoute route) {
        log.info("Add route : " + route.getPath());
        routes.add(route);
    }


    public WebRoute lookup(WebContext webContext, WebMethod verb, String uri) {
        Map<String, String> pathVariables;
        for (WebRoute<?, ?, ?> route : routes) {
            pathVariables = route.canApply(verb, uri);
            if (pathVariables != null) {
                webContext.setPathVariables(pathVariables);
                return route;
            }
        }
        return null;
    }


    public <T, FORM extends BindingForm, RES extends WebResponse<T>> WebRoute<T, FORM, RES>
    add(WebMethod method, String path, WebFormHandler<T, FORM, RES> handler) {
        WebRoute<T, FORM, RES> route = new WebRoute<>(handler).path(path).method(method);
        addRoutePrivate(route);
        return route;
    }

    public <T, FORM extends BindingForm, RES extends WebResponse<T>> WebRoute<T, FORM, RES>
    add(String path, WebFormHandler<T, FORM, RES> handler) {
        return add(WebMethod.GET, path, handler);
    }

    public <T, RES extends WebResponse<T>> WebRoute<T, BindingForm, RES>
    add(WebMethod method, String path, WebHandler<T, RES> handler) {
        return add(method, path, form -> handler.get());
    }

    public <T, RES extends WebResponse<T>> WebRoute<T, BindingForm, RES>
    add(String path, WebHandler<T, RES> handler) {
        return add(WebMethod.GET, path, form -> handler.get());
    }
}
