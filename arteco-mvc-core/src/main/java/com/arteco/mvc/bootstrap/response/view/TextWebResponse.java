package com.arteco.mvc.bootstrap.response.view;

import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.response.Renders;
import lombok.Data;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class TextWebResponse implements WebResponse<String> {
    private final String text;
    private MimeType mime = MimeType.TEXT;

    public TextWebResponse(String text) {
        this.text = text;
    }

    public TextWebResponse(String text, MimeType mime) {
        this(text);
        this.mime = mime;
    }

    @Override
    public void process(WebContext webContext) {
        Renders.write(webContext, text, mime);
    }
}
