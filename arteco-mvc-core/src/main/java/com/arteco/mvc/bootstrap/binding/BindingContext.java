package com.arteco.mvc.bootstrap.binding;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.converter.WebConversion;
import com.arteco.mvc.bootstrap.i18n.WebMessages;
import com.arteco.mvc.bootstrap.utils.ObjectUtils;
import lombok.Data;

import java.util.Locale;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class BindingContext<FORM extends BindingForm> {


    private final FORM form;
    private final WebContext webContext;
    private final WebConversion conversion;

    public BindingContext(FORM form, WebContext webContext) {
        this.form = form;
        this.webContext = webContext;
        this.conversion = webContext.getApp().getConverters();
    }

    public BindingType getType(Binding binding) {
        BindingType type = binding.getType();
        if (type == null) {
            String name = binding.getName();
            type = ObjectUtils.getType(form, name);
            binding.setType(type);
        }
        return type;
    }

    public void setValue(Binding binding, Object value) {
        if (value != null) {
            BindingType type = getType(binding);
            if (!type.isAssignableFrom(value.getClass())) {
                try {
                    value = conversion.convert(value, type.getType());
                } catch (Exception e) {
                    addError(binding, WebMessages.ERROR_CONVERSION, value, type, e.getMessage());
                    return;
                }
            }
            ObjectUtils.setPropertyValue(form, binding.getName(), value);
        }
    }

    public Object getValue(Binding binding) {
        return ObjectUtils.getPropertyValue(form, binding.getName());
    }

    public void addError(Binding binding, String errorKeyMessage, Object... args) {
        BindingResult results = form.getBindingResult();
        Locale locale = webContext.getLocale();
        String message = webContext.getApp().getTranslator().translate(locale, errorKeyMessage, args);
        results.errors.add(new BindingError(binding, message));
    }

    public void addError(Binding binding, String nameSuffix, String errorKeyMessage, Object... args) {
        BindingResult results = form.getBindingResult();
        Locale locale = webContext.getLocale();
        String message = webContext.getApp().getTranslator().translate(locale, errorKeyMessage, args);
        results.errors.add(new BindingError(binding, nameSuffix, message));
    }
}
