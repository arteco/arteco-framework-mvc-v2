package com.arteco.mvc.bootstrap;

import com.arteco.mvc.bootstrap.router.WebMethod;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.security.WebUser;
import com.arteco.mvc.bootstrap.security.authentication.WebAuthenticator;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class WebServletContext implements WebContext {
    private final WebApp app;
    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;

    private Locale locale;
    private WebRoute webRoute;
    private Map<String, String> pathVariables;
    private WebResponse response;
    private DataSource jdbcDataSource;
    private Connection jdbcConnection;

    private WebUser user;
    private WebAuthenticator authenticator;

    private Map<String, Object> attributes = new HashMap<>();

    WebServletContext(WebApp application, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        this.app = application;
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    @Override
    public String getURI() {
        return httpRequest.getRequestURI();
    }

    @Override
    public WebMethod getVerb() {
        String method = httpRequest.getMethod();
        if (EnumUtils.isValidEnum(WebMethod.class, method)) {
            return WebMethod.valueOf(method);
        }
        return null;
    }

    @Override
    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public <T> T getAttribute(Class<? extends T> clazz) {
        return (T) attributes.get(clazz.getName());
    }

    @Override
    public void setAttribute(Object value) {
        if (value != null) {
            this.attributes.put(value.getClass().getName(), value);
        }
    }

    @Override
    public String getMessage(String key, Object[] args) {
        return app.getTranslator().translate(locale, key, args);
    }

    public String toString() {
        return app.getInfo().getName();
    }
}
