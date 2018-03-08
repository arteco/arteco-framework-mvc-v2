package com.arteco.mvc.swagger.schema;

import com.arteco.mvc.bootstrap.binding.BindingType;
import lombok.Data;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class SchemaDefinition<DESCRIPTOR extends SwaggerDescriptor> {
    String name;
    BindingType type;
    DESCRIPTOR descriptor;
    boolean definable;

    public SchemaDefinition(String name, BindingType type, DESCRIPTOR descriptor, boolean definable) {
        this.name = name;
        this.type = type;
        this.descriptor = descriptor;
        this.definable = definable;
    }
}