package com.arteco.mvc.swagger.v3;

import com.arteco.mvc.swagger.schema.SwaggerDescriptor;
import io.swagger.v3.oas.models.media.Schema;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface SchemaDescriptor extends SwaggerDescriptor {
    Schema create(SchemaScope scope);
}
