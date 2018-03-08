package com.arteco.mvc.swagger.v2.schema;

import com.arteco.mvc.swagger.v2.ModelPropertyDescriptor;
import io.swagger.models.properties.BaseIntegerProperty;
import io.swagger.models.properties.Property;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class IntegerPropertyDescriptor implements ModelPropertyDescriptor {
    @Override
    public Property getProperty() {
        return new BaseIntegerProperty();
    }

    @Override
    public String getType() {
        return BaseIntegerProperty.TYPE;
    }
}
