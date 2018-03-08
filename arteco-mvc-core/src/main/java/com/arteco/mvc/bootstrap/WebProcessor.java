package com.arteco.mvc.bootstrap;

import com.arteco.mvc.bootstrap.chain.WebChain;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@FunctionalInterface
public interface WebProcessor {

    void process(WebContext webContext, WebChain chain) throws Exception;
}
