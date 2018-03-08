package com.arteco.mvc.swagger;


import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebModule;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;

public class SwaggerWebModule implements WebModule {

    private final SwaggerFactory factory;

    public SwaggerWebModule(SwaggerFactory factory) {
        this.factory = factory;
    }

    public static <T, FORM extends BindingForm, RES extends WebResponse<T>> SwaggerFeature<T, FORM, RES> swagger(Class<T> responseType, String description) {
        return new SwaggerFeature<>(responseType, description);
    }

    @Override
    public void configure(WebApp webApp) {
        webApp.addController(new SwaggerController(factory, webApp));
    }
}
