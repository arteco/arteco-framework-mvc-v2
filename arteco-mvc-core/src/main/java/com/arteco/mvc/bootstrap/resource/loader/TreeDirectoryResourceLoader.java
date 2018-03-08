package com.arteco.mvc.bootstrap.resource.loader;

import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceLoader;
import com.arteco.mvc.bootstrap.resource.WebResourceUtils;

import java.util.List;

/**
 * Created by rarnau on 1/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class TreeDirectoryResourceLoader implements WebResourceLoader {
    private final String extension;
    private final String pathDir;

    public TreeDirectoryResourceLoader(String pathDir, String extension) {
        this.pathDir = pathDir;
        this.extension = extension;
    }

    @Override
    public List<WebResource> getResources() {
        return WebResourceUtils.listResources(pathDir, extension);
    }
}
