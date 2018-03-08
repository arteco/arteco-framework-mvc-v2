package com.arteco.mvc.bootstrap;

import com.arteco.mvc.bootstrap.router.WebMethod;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.security.WebUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebContext {
    Map<String, String> getPathVariables();

    void setPathVariables(Map<String, String> pathVariables);

    String getURI();

    WebMethod getVerb();

    WebApp getApp();

    WebRoute getWebRoute();

    void setWebRoute(WebRoute handler);

    HttpServletResponse getHttpResponse();

    HttpServletRequest getHttpRequest();

    WebResponse getResponse();

    void setResponse(WebResponse response);

    Locale getLocale();

    void setLocale(Locale locale);

    DataSource getJdbcDataSource();

    void setJdbcDataSource(DataSource dataSource);

    Connection getJdbcConnection();

    void setJdbcConnection(Connection con);

    WebUser getUser();

    void setUser(WebUser user);

    Object getAttribute(String attributeName);

    void setAttribute(String name, Object value);

    <T> T getAttribute(Class<? extends T> clazz);

    void setAttribute(Object value);

    String getMessage(String key, Object[] args);
}
