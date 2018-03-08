package com.arteco.mvc.swagger.v2.schema;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.swagger.v2.ModelPropertyDescriptor;
import com.arteco.mvc.swagger.v2.SwaggerModelPropertyManager;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class ArrayPropertyDescriptor implements ModelPropertyDescriptor {

    private final String name;
    private final SwaggerModelPropertyManager schemaManager;
    private final BindingType bindingType;

    public ArrayPropertyDescriptor(BindingType bindingType, String name, SwaggerModelPropertyManager schemaManager) {
        this.bindingType = bindingType;
        this.name = name;
        this.schemaManager = schemaManager;
    }


    @Override
    public Property getProperty() {
        Property prop = schemaManager.getSchemaDefinition(bindingType.getComponentType()).getDescriptor().getProperty();
        return new ArrayProperty(prop);
    }

    @Override
    public String getType() {
        return ArrayProperty.TYPE;
    }
}
