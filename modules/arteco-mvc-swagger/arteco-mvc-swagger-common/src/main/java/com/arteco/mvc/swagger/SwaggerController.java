package com.arteco.mvc.swagger;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebController;
import com.arteco.mvc.bootstrap.router.RouterRegister;
import com.arteco.mvc.bootstrap.router.WebMethod;

import static com.arteco.mvc.bootstrap.response.Responses.json;
import static com.arteco.mvc.bootstrap.response.Responses.yaml;

public class SwaggerController implements WebController {
    private final WebApp webApp;
    private final SwaggerFactory swaggerFactory;
    private final String version;

    public SwaggerController(SwaggerFactory factory, WebApp webApp) {
        this.swaggerFactory = factory;
        this.webApp = webApp;
        this.version = factory.getVersion();
    }

    @Override
    public void registerPaths(RouterRegister paths) {
        paths.add(WebMethod.GET, "/" + version + "/api-docs", () -> {
            Object apiDoc = swaggerFactory.generateApiDoc(webApp);
            return json(apiDoc);
        });


        paths.add(WebMethod.GET, "/" + version + "/api-docs/yaml", () -> {
            Object apiDoc = swaggerFactory.generateApiDoc(webApp);
            return yaml(apiDoc);
        });


    }
}
