package com.arteco.mvc.bootstrap.response.view;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.response.Renders;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class TextWebResponse implements WebResponse<String> {
    private final String text;

    public TextWebResponse(String text) {
        this.text = text;
    }

    @Override
    public void process(WebContext webContext) {
        Renders.writeText(webContext, text);
    }
}
