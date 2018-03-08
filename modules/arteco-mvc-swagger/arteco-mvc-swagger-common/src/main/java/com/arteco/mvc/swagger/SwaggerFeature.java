package com.arteco.mvc.swagger;

import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.router.feature.WebFeature;
import lombok.Data;

import java.util.List;

@Data
public class SwaggerFeature<T, FORM extends BindingForm, RES extends WebResponse<T>> implements WebFeature<T, FORM, RES> {
    private final Class<T> responseType;

    private String summary;
    private String description;
    private List<String> tags;

    public SwaggerFeature(Class<T> responseType, String description) {
        this.responseType = responseType;
        this.description = description;
    }

    @Override
    public void complete(WebRoute<T, FORM, RES> route) {
        route.setResponseObjectType(responseType);
    }
}
