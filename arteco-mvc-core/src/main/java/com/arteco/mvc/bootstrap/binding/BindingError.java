package com.arteco.mvc.bootstrap.binding;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class BindingError {
    private String name;
    private String message;

    public BindingError(String message) {
        this.message = message;
    }

    public BindingError(Binding binding, String message) {
        this.message = message;
        this.name = binding.getName();
    }

    public BindingError(Binding binding, String nameSuffix, String errorMessage) {
        this.message = errorMessage;
        this.name = StringUtils.appendIfMissing(binding.getName(), ".") + nameSuffix;
    }
}
