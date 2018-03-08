package com.arteco.mvc.bootstrap.binding.processor;

import com.arteco.mvc.bootstrap.binding.*;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DefaultValue implements FormBinder {

    private final Object defaultValue;

    public DefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }


    @Override
    public <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext, Binding binding) {
        String name = binding.getName();
        BindingType type = bindingContext.getType(binding);
        Object value = binding.getScope().apply(bindingContext, name, type);
        if (value == null) {
            value = defaultValue;
        }
        bindingContext.setValue(binding, value);
    }
}
