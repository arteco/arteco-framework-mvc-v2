package com.arteco.mvc.swagger.v3.schema;

import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.v3.SchemaDescriptor;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class BasicSchemaDefinition extends SchemaDefinition<SchemaDescriptor> {
    public BasicSchemaDefinition(SchemaDescriptor schemaResolver) {
        super(null, null, schemaResolver, false);
    }
}
