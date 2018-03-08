package com.arteco.mvc.ajaxui.components.base;

import com.arteco.mvc.ajaxui.components.form.FieldDescriptor;
import com.arteco.mvc.bootstrap.binding.BindingForm;

import java.util.List;

/**
 * Created by rarnau on 5/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public abstract class IOBindingForm extends BindingForm {
    public abstract List<FieldDescriptor> getFields();
}
