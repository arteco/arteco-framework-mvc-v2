package com.arteco.mvc.swagger.v3.schema;

import com.arteco.mvc.swagger.v3.SchemaDescriptor;
import com.arteco.mvc.swagger.v3.SchemaScope;
import io.swagger.v3.oas.models.media.IntegerSchema;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class IntegerSchemaResolver implements SchemaDescriptor {
    @Override
    public IntegerSchema create(SchemaScope schemaScope) {
        return new IntegerSchema();
    }
}
