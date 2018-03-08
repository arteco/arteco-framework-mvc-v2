package com.arteco.mvc.swagger;

import com.arteco.mvc.bootstrap.WebApp;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface SwaggerFactory {
    Object generateApiDoc(WebApp webApp);

    String getVersion();
}
