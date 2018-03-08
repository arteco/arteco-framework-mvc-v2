package com.arteco.mvc.swagger.schema;

import com.arteco.mvc.bootstrap.binding.BindingType;

import java.util.Iterator;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface SchemaManager<DESCRIPTOR extends SwaggerDescriptor> {
    SchemaDefinition<DESCRIPTOR> getSchemaDefinition(Class type);

    SchemaDefinition<DESCRIPTOR> getSchemaDefinition(BindingType bindingType);

    Iterator<SchemaDefinition<DESCRIPTOR>> getAllSchemas();
}
