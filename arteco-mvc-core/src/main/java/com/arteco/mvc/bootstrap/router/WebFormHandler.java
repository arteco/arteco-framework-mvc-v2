package com.arteco.mvc.bootstrap.router;

import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;

import java.util.function.Function;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@FunctionalInterface
public interface WebFormHandler<T, FORM extends BindingForm, RES extends WebResponse<T>> extends Function<FORM, RES> {

}
