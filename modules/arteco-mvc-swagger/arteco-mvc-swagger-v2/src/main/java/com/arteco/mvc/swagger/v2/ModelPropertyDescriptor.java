package com.arteco.mvc.swagger.v2;

import com.arteco.mvc.swagger.schema.SwaggerDescriptor;
import io.swagger.models.Model;
import io.swagger.models.properties.Property;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface ModelPropertyDescriptor extends SwaggerDescriptor {
    default Model getModel() {
        return null;
    }

    Property getProperty();

    String getType();
}
