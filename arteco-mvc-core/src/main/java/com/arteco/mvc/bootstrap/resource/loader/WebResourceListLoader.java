package com.arteco.mvc.bootstrap.resource.loader;


import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rarnau on 1/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class WebResourceListLoader implements WebResourceLoader {
    private final List<WebResource> resources;

    public WebResourceListLoader(WebResource... resources) {
        this.resources = new ArrayList<>();
        if (resources != null) {
            this.resources.addAll(Arrays.asList(resources));
        }
    }

    @Override
    public List<WebResource> getResources() {
        return resources;
    }
}
