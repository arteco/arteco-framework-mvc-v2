package com.arteco.mvc.swagger.v2.schema;

import com.arteco.mvc.swagger.v2.ModelPropertyDescriptor;
import io.swagger.models.properties.DecimalProperty;
import io.swagger.models.properties.Property;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class NumberPropertyDescriptor implements ModelPropertyDescriptor {
    @Override
    public Property getProperty() {
        return new DecimalProperty();
    }

    @Override
    public String getType() {
        return DecimalProperty.TYPE;
    }
}
