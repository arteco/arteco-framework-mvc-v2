package com.arteco.mvc.swagger.v2.schema;

import com.arteco.mvc.swagger.v2.ModelPropertyDescriptor;
import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DatePropertyDescriptor implements ModelPropertyDescriptor {
    @Override
    public Property getProperty() {
        return new DateProperty();
    }

    @Override
    public String getType() {
        return StringProperty.TYPE;
    }
}
