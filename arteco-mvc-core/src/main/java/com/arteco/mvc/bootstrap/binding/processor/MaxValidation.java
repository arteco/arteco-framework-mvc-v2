package com.arteco.mvc.bootstrap.binding.processor;

import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.binding.BindingContext;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.binding.FormBinder;
import com.arteco.mvc.bootstrap.i18n.WebMessages;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class MaxValidation implements FormBinder {

    private final int max;

    public MaxValidation(int max) {
        this.max = max;
    }

    @Override
    public <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext, Binding binding) {
        Object value = bindingContext.getValue(binding);
        if (value != null) {
            if (value instanceof Collection && ((Collection) value).size() > max) {
                bindingContext.addError(binding, WebMessages.ERROR_MAX_LIMIT, max);
            } else if (value instanceof String && ((String) value).length() > max) {
                bindingContext.addError(binding, WebMessages.ERROR_MAX_LIMIT, max);
            } else if (value instanceof Number && ((Number) value).longValue() > max) {
                bindingContext.addError(binding, WebMessages.ERROR_MAX_LIMIT, max);
            } else if (value.getClass().isArray() && Array.getLength(value) > max) {
                bindingContext.addError(binding, WebMessages.ERROR_MAX_LIMIT, max);
            }
        }
    }
}
