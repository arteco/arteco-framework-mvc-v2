package com.arteco.mvc.bootstrap.response.view;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarnau on 24/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class ViewWebResponse implements WebResponse<String> {

    private final String viewName;
    private Map<String, Object> attributes = new HashMap<>();

    public ViewWebResponse(String viewName) {
        this.viewName = viewName;
    }

    public ViewWebResponse with(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public void process(WebContext webContext) {
        WebViewEngine engine = webContext.getWebRoute().getViewEngine();
        if (engine == null) {
            engine = webContext.getApp().getViewEngine();
        }
        if (engine == null) {
            throw ExceptionUtils.trigger("No View Engine Defined for [" + viewName + "]");
        }
        engine.process(webContext, this);
    }
}
