package com.arteco.mvc.swagger.v2.schema;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.bootstrap.utils.ObjectUtils;
import com.arteco.mvc.swagger.SwaggerUtils;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.v2.ModelPropertyDescriptor;
import com.arteco.mvc.swagger.v2.SwaggerModelPropertyManager;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.arteco.mvc.swagger.v2.SwaggerV2Factory.SCHEMA_PREFIX;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class ObjectPropertyDescriptor implements ModelPropertyDescriptor {
    private final SwaggerModelPropertyManager schemaManager;
    private final BindingType bindingType;
    private final String name;

    public ObjectPropertyDescriptor(BindingType bindingType, String name, SwaggerModelPropertyManager schemaManager) {
        this.schemaManager = schemaManager;
        this.bindingType = bindingType;
        this.name = name;
    }

    @Override
    public Model getModel() {
        ModelImpl schema = new ModelImpl();
        Map<String, Property> properties = new HashMap<>();
        Class type = bindingType.getType();
        Field[] fields = ObjectUtils.getFields(type);
        for (Field f : fields) {
            if (SwaggerUtils.shouldInclude(f)) {
                SchemaDefinition<ModelPropertyDescriptor> definition = schemaManager.getSchemaDefinition(new BindingType(f));
                if (definition.isDefinable()) {
                    properties.put(f.getName(), new RefProperty(SCHEMA_PREFIX + definition.getName()));
                } else {
                    properties.put(f.getName(), definition.getDescriptor().getProperty());
                }
            }
        }
        schema.setProperties(properties);
        return schema;
    }

    @Override
    public Property getProperty() {
        return new RefProperty(SCHEMA_PREFIX + name);
    }

    @Override
    public String getType() {
        throw new NotImplementedException("");
    }
}
