package com.arteco.mvc.bootstrap.response.view;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.chain.WebChain;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DefaultRenderProc implements WebProcessor {
    @Override
    public void process(WebContext webContext, WebChain chain) throws Exception {
        chain.process();
        WebResponse response = webContext.getResponse();
        if (response != null) {
            response.process(webContext);
        }
    }
}
