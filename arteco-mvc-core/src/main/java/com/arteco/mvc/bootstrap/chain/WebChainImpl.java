package com.arteco.mvc.bootstrap.chain;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;

import java.util.List;

public class WebChainImpl implements WebChain {

    final List<WebProcessor> processors;

    int location;
    private WebContext webContext;

    public WebChainImpl(WebContext webContext, List<WebProcessor> processors) {
        this.location = 0;
        this.webContext = webContext;
        this.processors = processors;
    }

    public void process() throws Exception {
        int index = this.location++;
        if (index < this.processors.size()) {
            WebProcessor proc = this.processors.get(index);
            proc.process(webContext, this);
        }
    }
}
