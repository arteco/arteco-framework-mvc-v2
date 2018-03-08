package com.arteco.mvc.swagger.v3.schema;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.bootstrap.utils.ObjectUtils;
import com.arteco.mvc.swagger.SwaggerUtils;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.schema.SchemaManager;
import com.arteco.mvc.swagger.v3.SchemaDescriptor;
import com.arteco.mvc.swagger.v3.SchemaScope;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.arteco.mvc.swagger.v3.SwaggerV3Factory.SCHEMA_PREFIX;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class ObjectSchemaResolver implements SchemaDescriptor {
    private final SchemaManager<SchemaDescriptor> schemaManager;
    private final BindingType bindingType;
    private final String typeName;

    public ObjectSchemaResolver(BindingType bindingType, String typeName, SchemaManager<SchemaDescriptor> schemaManager) {
        this.schemaManager = schemaManager;
        this.bindingType = bindingType;
        this.typeName = typeName;
    }

    @Override
    public Schema create(SchemaScope scope) {
        switch (scope) {
            case REFERENCE:
                return getReference();
            case DEFINITION:
                return getDefinition();
            default:
                throw ExceptionUtils.trigger("Scope not recognized");
        }
    }

    private Schema getDefinition() {
        ObjectSchema schema = new ObjectSchema();
        Map<String, Schema> properties = new HashMap<>();
        Class type = bindingType.getType();
        Field[] fields = ObjectUtils.getFields(type);
        for (Field f : fields) {
            if (SwaggerUtils.shouldInclude(f)) {
                SchemaDefinition<SchemaDescriptor> definition = schemaManager.getSchemaDefinition(new BindingType(f));
                BindingType propBinding = definition.getType();
                Schema propSchema = getPropSchema(definition, propBinding);
                properties.put(f.getName(), propSchema);
            }
        }
        schema.setProperties(properties);
        return schema;
    }

    private Schema getPropSchema(SchemaDefinition<SchemaDescriptor> definition, BindingType propBinding) {
        Schema propSchema;
        if (propBinding.isCollection()) {
            definition.setDefinable(false);
            BindingType componentType = new BindingType(definition.getType().getComponentType());
            SchemaDescriptor componentDescriptor = schemaManager.getSchemaDefinition(componentType).getDescriptor();

            ArraySchema arraySchema = new ArraySchema();
            arraySchema.items(componentDescriptor.create(SchemaScope.REFERENCE));
            propSchema = arraySchema;
        } else {
            SchemaDescriptor descriptor = definition.getDescriptor();
            propSchema = descriptor.create(SchemaScope.REFERENCE);
        }
        return propSchema;
    }

    private Schema getReference() {
        ObjectSchema propSchema = new ObjectSchema();
        propSchema.$ref(SCHEMA_PREFIX + typeName);
        return propSchema;
    }

}
