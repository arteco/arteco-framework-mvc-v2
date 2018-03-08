package com.arteco.mvc.bootstrap.binding;

import lombok.Data;

import java.util.List;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public abstract class BindingForm {
    BindingResult bindingResult = new BindingResult();

    public abstract List<Binding> define();
}
