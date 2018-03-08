package com.arteco.mvc.swagger.v2.schema;

import com.arteco.mvc.swagger.v2.ModelPropertyDescriptor;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.UUIDProperty;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class UUIDPropertyDescriptor implements ModelPropertyDescriptor {


    @Override
    public Property getProperty() {
        return new UUIDProperty();
    }

    @Override
    public String getType() {
        return "string";
    }
}
