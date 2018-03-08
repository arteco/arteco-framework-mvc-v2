package com.arteco.mvc.bootstrap.error;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.chain.WebChain;
import com.arteco.mvc.bootstrap.response.NotFoundResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DefaultNotFoundProc implements WebProcessor {


    @Override
    public void process(WebContext webContext, WebChain chain) {
        webContext.getHttpResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
        new NotFoundResponse().process(webContext);
    }
}
