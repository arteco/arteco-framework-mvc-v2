package com.arteco.mvc.bootstrap.response;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class NotFoundResponse implements WebResponse {
    @Override
    public void process(WebContext webContext) {
        log.info("Not Found [{}]", webContext.getHttpRequest().getRequestURI());
        webContext.getHttpResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
