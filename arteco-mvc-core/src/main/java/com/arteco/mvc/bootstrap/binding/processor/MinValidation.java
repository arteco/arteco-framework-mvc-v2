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
public class MinValidation implements FormBinder {

    private final int min;
    private final boolean nullable;

    public MinValidation(int min) {
        this.min = min;
        this.nullable = true;
    }

    public MinValidation(int min, boolean nullable) {
        this.min = min;
        this.nullable = nullable;
    }

    @Override
    public <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext, Binding binding) {
        Object value = bindingContext.getValue(binding);
        if (value != null) {
            if (value instanceof Collection && ((Collection) value).size() < min) {
                bindingContext.addError(binding, WebMessages.ERROR_MIN_LIMIT, min);
            } else if (value instanceof String && ((String) value).length() < min) {
                bindingContext.addError(binding, WebMessages.ERROR_MIN_LIMIT, min);
            } else if (value instanceof Number && ((Number) value).longValue() < min) {
                bindingContext.addError(binding, WebMessages.ERROR_MIN_LIMIT, min);
            } else if (value.getClass().isArray() && Array.getLength(value) < min) {
                bindingContext.addError(binding, WebMessages.ERROR_MIN_LIMIT, min);
            }
        } else if (!nullable) {
            bindingContext.addError(binding, WebMessages.ERROR_MIN_LIMIT, min);
        }
    }
}
