package com.arteco.mvc.bootstrap.response;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.Data;

import java.io.IOException;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class RedirectWebResponse implements WebResponse<Void> {
    private final String redirection;

    public RedirectWebResponse(String redirection) {
        this.redirection = redirection;
    }

    @Override
    public void process(WebContext webContext) {
        try {
            webContext.getHttpResponse().sendRedirect(redirection);
        } catch (IOException e) {
            throw ExceptionUtils.manage(e);
        }
    }
}
