package com.arteco.mvc.bootstrap.binding;

import com.arteco.mvc.bootstrap.binding.processor.*;
import com.arteco.mvc.bootstrap.utils.ObjectUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class Binding {
    private final String name;
    private final BindingScope scope;
    private final List<FormBinder> processors = new ArrayList<>();

    private BindingType type;
    private Class<? extends BindingForm> formType;
    private String description;
    private Boolean obligatory;

    public Binding(String name, BindingScope scope) {
        this.name = name;
        this.scope = scope;
    }


    public BindingType getType() {
        if (type == null) {
            type = ObjectUtils.getType(formType, name);
        }
        return type;
    }

    public void add(FormBinder formBinder) {
        processors.add(formBinder);
    }

    public Binding obligatory(boolean isObligatory) {
        if (isObligatory) {
            obligatory = true;
            processors.add(NotEmptyValidation.getInstance());
        }
        return this;
    }

    public Binding min(int min) {
        processors.add(new MinValidation(min));
        return this;
    }

    public Binding max(int max) {
        processors.add(new MaxValidation(max));
        return this;
    }

    public Binding description(String description) {
        this.description = description;
        return this;
    }

    public Binding defaultValue(Object defaultValue) {
        if (defaultValue != null) {
            processors.add(new DefaultValue(defaultValue));
        }
        return this;
    }

    public Binding valid(boolean shouldValidate) {
        if (shouldValidate) {
            processors.add(JSR303Validation.getInstance());
        }
        return this;
    }

    public <FORM extends BindingForm> void evaluate(BindingContext<FORM> bindingContext) {
        Object value = scope.apply(bindingContext, this);
        bindingContext.setValue(this, value);
        for (FormBinder processor : processors) {
            processor.evaluate(bindingContext, this);
        }
    }

    public boolean isObligatory() {
        return obligatory != null && obligatory;
    }
}
