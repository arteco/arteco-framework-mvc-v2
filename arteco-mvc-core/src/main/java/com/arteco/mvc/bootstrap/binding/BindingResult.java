package com.arteco.mvc.bootstrap.binding;

import com.arteco.mvc.bootstrap.binding.annotation.WebCollection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class BindingResult {
    @WebCollection(type = BindingError.class)
    List<BindingError> errors = new ArrayList<>();

    public boolean hasErrors() {
        return errors != null && errors.size() > 0;
    }

    public boolean hasGlobalErrors() {
        for (BindingError err : errors) {
            if (err.getName() == null) {
                return true;
            }
        }
        return false;
    }

    public List<BindingError> getGlobalErrors() {
        List<BindingError> errors = new ArrayList<>();
        for (BindingError err : errors) {
            if (err.getName() == null) {
                errors.add(err);
            }
        }
        return errors;
    }
}
