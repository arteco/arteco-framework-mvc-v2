package com.arteco.mvc.bootstrap.resource.backend;

import com.arteco.mvc.bootstrap.resource.WebResource;

import java.util.List;

public interface WebResourceBackEnd {
    WebResource getResource(String pathFile);

    List<WebResource> listResources(String pathDir, String extension);
}
