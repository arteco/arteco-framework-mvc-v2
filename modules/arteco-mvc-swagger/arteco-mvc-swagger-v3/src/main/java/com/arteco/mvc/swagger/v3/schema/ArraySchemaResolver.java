package com.arteco.mvc.swagger.v3.schema;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.v3.SchemaDescriptor;
import com.arteco.mvc.swagger.v3.SchemaScope;
import com.arteco.mvc.swagger.v3.SwaggerSchemaManager;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;

public class ArraySchemaResolver implements SchemaDescriptor {
    private final SwaggerSchemaManager schemaManager;
    private final String name;
    private final BindingType bindingType;

    public ArraySchemaResolver(BindingType bindingType, String name, SwaggerSchemaManager swaggerSchemaManager) {
        this.bindingType = bindingType;
        this.name = name;
        this.schemaManager = swaggerSchemaManager;
    }

    @Override
    public Schema create(SchemaScope scope) {
        ArraySchema schema = new ArraySchema();
        SchemaDefinition<SchemaDescriptor> definition = schemaManager.getSchemaDefinition(bindingType.getComponentType());
        schema.items(definition.getDescriptor().create(SchemaScope.REFERENCE));
        return schema;
    }
}
