package com.arteco.mvc.bootstrap.error;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.chain.WebChain;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class DefaultExceptionProc implements WebProcessor {
    @Override
    public void process(WebContext webContext, WebChain chain) {
        try {
            chain.process();
        } catch (Exception e) {
            webContext.getApp().getExceptionManager().manage(webContext, e);
        }
    }


}
