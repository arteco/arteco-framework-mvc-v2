package com.arteco.mvc.bootstrap.binding;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface FormBinder {
    <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext, Binding binding);

}
