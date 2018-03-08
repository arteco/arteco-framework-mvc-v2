package com.arteco.mvc.bootstrap.response;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceLoader;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rarnau on 1/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public abstract class CompilableResponse<T> implements WebResponse<T> {

    private final WebResourceLoader resourceLoader;

    public CompilableResponse(WebResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void process(WebContext webContext) {
        MutableObject<Long> lastModified = new MutableObject<>();
        List<WebResource> resources = getLastModified(lastModified);
        IfModified.ifModifiedSince(webContext, lastModified.getValue(), ctx -> compileResource(ctx, resources));
    }

    protected abstract void compileResource(WebContext webContext, List<WebResource> resources);

    private List<WebResource> getLastModified(MutableObject<Long> mutableLastModified) {
        long lastModified = 0;
        List<WebResource> resources = new ArrayList<>();
        for (WebResource resource : resourceLoader.getResources()) {
            if (lastModified < resource.getLastModified()) {
                lastModified = resource.getLastModified();
            }
            resources.add(resource);
        }
        mutableLastModified.setValue(lastModified);
        return resources;
    }
}
