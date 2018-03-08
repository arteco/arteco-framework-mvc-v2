package com.arteco.mvc.bootstrap.response.yaml;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.response.Renders;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class YamlWebResponse<T> implements WebResponse<T> {
    private final T value;
    private final int httpStatus;

    public YamlWebResponse(T object) {
        this(object, HttpServletResponse.SC_OK);
    }

    public YamlWebResponse(T object, int httpStatus) {
        this.value = object;
        this.httpStatus = httpStatus;
    }

    @Override
    public void process(WebContext webContext) {
        webContext.getHttpResponse().setStatus(httpStatus);
        String text = webContext.getApp().getYamlEngine().toYaml(value);
        Renders.writeYaml(webContext, text);
    }

    @Override
    public T getValue() {
        return value;
    }
}
