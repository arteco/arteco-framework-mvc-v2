package com.arteco.mvc.bootstrap.binding.processor;

import com.arteco.mvc.bootstrap.WebThread;
import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.binding.BindingContext;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.binding.FormBinder;

import javax.validation.*;
import java.util.Locale;
import java.util.Set;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class JSR303Validation implements FormBinder {
    private static JSR303Validation instance = new JSR303Validation();

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation
                .byDefaultProvider()
                .configure()
                .messageInterpolator(new MessageInterpolator() {
                    @Override
                    public String interpolate(String messageTemplate, Context context) {
                        Locale locale = WebThread.get().getLocale();
                        return interpolate(messageTemplate, context, locale);
                    }

                    @Override
                    public String interpolate(String messageTemplate, Context context, Locale locale) {
                        //TODO: mejorar la información del error
                        return WebThread.get().getApp().getTranslator().translate(locale, messageTemplate, null);
                    }
                })
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    private JSR303Validation() {
    }

    public static JSR303Validation getInstance() {
        return instance;
    }

    @Override
    public <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext, Binding binding) {
        Object value = bindingContext.getValue(binding);
        if (value != null) {
            Set<ConstraintViolation<Object>> errors = validator.validate(value);
            if (errors != null) {
                for (ConstraintViolation<Object> error : errors) {
                    bindingContext.addError(binding, error.getPropertyPath().toString(), error.getMessage());
                }
            }
        }
    }
}
