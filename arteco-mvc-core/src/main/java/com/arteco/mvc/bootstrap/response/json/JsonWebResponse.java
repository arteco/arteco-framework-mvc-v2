package com.arteco.mvc.bootstrap.response.json;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.response.Renders;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class JsonWebResponse<T> implements WebResponse<T> {
    private final T value;
    private final int httpStatus;

    public JsonWebResponse(T object) {
        this(object, HttpServletResponse.SC_OK);
    }

    public JsonWebResponse(T object, int httpStatus) {
        this.value = object;
        this.httpStatus = httpStatus;
    }

    @Override
    public void process(WebContext webContext) {
        webContext.getHttpResponse().setStatus(httpStatus);
        String text = webContext.getApp().getJsonEngine().toJson(value);
        Renders.writeJson(webContext, text);
    }

    @Override
    public T getValue() {
        return value;
    }
}
