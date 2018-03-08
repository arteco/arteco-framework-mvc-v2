package com.arteco.mvc.swagger.v3.schema;

import com.arteco.mvc.swagger.v3.SchemaDescriptor;
import com.arteco.mvc.swagger.v3.SchemaScope;
import io.swagger.v3.oas.models.media.NumberSchema;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class NumberSchemaResolver implements SchemaDescriptor {
    @Override
    public NumberSchema create(SchemaScope schemaScope) {
        return new NumberSchema();
    }
}
