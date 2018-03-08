package com.arteco.mvc.swagger.v2;

import com.arteco.mvc.swagger.schema.SchemaDefinition;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class BasicModAndPropDesc extends SchemaDefinition<ModelPropertyDescriptor> {

    public BasicModAndPropDesc(ModelPropertyDescriptor propertyDescriptor) {
        super(null, null, propertyDescriptor, false);
    }
}
