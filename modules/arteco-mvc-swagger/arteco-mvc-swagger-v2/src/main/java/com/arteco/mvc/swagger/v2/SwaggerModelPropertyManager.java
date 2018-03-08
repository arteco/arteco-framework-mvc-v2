package com.arteco.mvc.swagger.v2;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.swagger.AbstractSchemaManagerImpl;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.v2.schema.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class SwaggerModelPropertyManager extends AbstractSchemaManagerImpl<ModelPropertyDescriptor> {


    SwaggerModelPropertyManager() {
        super.addSchema(UUID.class, new BasicModAndPropDesc(new UUIDPropertyDescriptor()));
        super.addSchema(Integer.class, new BasicModAndPropDesc(new IntegerPropertyDescriptor()));
        super.addSchema(int.class, new BasicModAndPropDesc(new IntegerPropertyDescriptor()));
        super.addSchema(Float.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(float.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(Double.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(double.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(Long.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(long.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(Boolean.class, new BasicModAndPropDesc(new BooleanPropertyDescriptor()));
        super.addSchema(boolean.class, new BasicModAndPropDesc(new BooleanPropertyDescriptor()));
        super.addSchema(byte.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(short.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(char.class, new BasicModAndPropDesc(new NumberPropertyDescriptor()));
        super.addSchema(String.class, new BasicModAndPropDesc(new StringPropertyDescriptor()));
        super.addSchema(Date.class, new BasicModAndPropDesc(new DatePropertyDescriptor()));
    }

    @Override
    protected void forceDescriptionIntrospection(SchemaDefinition<ModelPropertyDescriptor> ce) {
        ce.getDescriptor().getModel();
    }

    @Override
    protected ModelPropertyDescriptor newArraySchemaResolver(BindingType bindingType, String name) {
        return new ArrayPropertyDescriptor(bindingType, name, this);
    }

    @Override
    protected ModelPropertyDescriptor newObjectSchemaResolver(BindingType bindingType, String name) {
        return new ObjectPropertyDescriptor(bindingType, name, this);
    }


}
