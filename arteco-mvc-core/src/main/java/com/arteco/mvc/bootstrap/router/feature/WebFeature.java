package com.arteco.mvc.bootstrap.router.feature;

import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.router.WebRoute;

public interface WebFeature<T, FORM extends BindingForm, RES extends WebResponse<T>> {
    void complete(WebRoute<T, FORM, RES> route);
}
