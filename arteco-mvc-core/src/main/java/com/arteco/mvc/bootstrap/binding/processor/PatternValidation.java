package com.arteco.mvc.bootstrap.binding.processor;

import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.binding.BindingContext;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.binding.FormBinder;
import com.arteco.mvc.bootstrap.i18n.WebMessages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class PatternValidation implements FormBinder {
    private final Pattern pattern;

    public PatternValidation(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext, Binding binding) {
        Object value = bindingContext.getValue(binding);
        if (value != null) {
            Matcher mat = pattern.matcher(value.toString());
            if (!mat.find()) {
                bindingContext.addError(binding, WebMessages.ERROR_PATTERN, pattern);
            }
        }
    }
}
