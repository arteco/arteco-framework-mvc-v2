package com.arteco.mvc.bootstrap.servlet;

import java.util.Map;

public interface Definition {
    String getName();

    String[] getUrlMappings();

    Map<String, String> getInitParameters();
}
