package com.arteco.mvc.swagger.v3;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.swagger.AbstractSchemaManagerImpl;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.v3.schema.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rarnau on 29/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class SwaggerSchemaManager extends AbstractSchemaManagerImpl<SchemaDescriptor> {


    SwaggerSchemaManager() {
        super.addSchema(UUID.class, new BasicSchemaDefinition(new UUIDSchemaResolver()));
        super.addSchema(Integer.class, new BasicSchemaDefinition(new IntegerSchemaResolver()));
        super.addSchema(int.class, new BasicSchemaDefinition(new IntegerSchemaResolver()));
        super.addSchema(Float.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(float.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(Double.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(double.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(Long.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(long.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(Boolean.class, new BasicSchemaDefinition(new BooleanSchemaResolver()));
        super.addSchema(boolean.class, new BasicSchemaDefinition(new BooleanSchemaResolver()));
        super.addSchema(byte.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(short.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(char.class, new BasicSchemaDefinition(new NumberSchemaResolver()));
        super.addSchema(String.class, new BasicSchemaDefinition(new StringSchemaResolver()));
        super.addSchema(Date.class, new BasicSchemaDefinition(new DateSchemaResolver()));

    }


    @Override
    protected void forceDescriptionIntrospection(SchemaDefinition<SchemaDescriptor> ce) {
        ce.getDescriptor().create(SchemaScope.DEFINITION);
    }

    @Override
    protected SchemaDescriptor newArraySchemaResolver(BindingType bindingType, String name) {
        return new ArraySchemaResolver(bindingType, name, this);
    }

    @Override
    protected SchemaDescriptor newObjectSchemaResolver(BindingType bindingType, String name) {
        return new ObjectSchemaResolver(bindingType, name, this);
    }


}
