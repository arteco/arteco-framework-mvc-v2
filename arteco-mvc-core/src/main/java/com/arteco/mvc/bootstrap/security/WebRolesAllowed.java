package com.arteco.mvc.bootstrap.security;

import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.router.feature.WebFeature;
import lombok.Data;

@Data
public class WebRolesAllowed<T, FORM extends BindingForm, RES extends WebResponse<T>> implements WebFeature<T, FORM, RES> {
    private final String[] roles;

    public WebRolesAllowed(String[] roles) {
        this.roles = roles;
    }

    @Override
    public void complete(WebRoute<T, FORM, RES> route) {
        // nothing to do;
    }
}
