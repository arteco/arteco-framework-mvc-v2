package com.arteco.mvc.bootstrap.chain;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;

import java.util.List;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DefaultChainRunner implements WebChainRunner {

    @Override
    public void process(WebContext webContext) {
        List<WebProcessor> processors = webContext.getApp().getProcessors();
        WebChain chain = new WebChainImpl(webContext, processors);
        try {
            chain.process();
        } catch (Exception e) {
            webContext.getApp().getExceptionManager().manage(webContext, e);
        }
    }
}
