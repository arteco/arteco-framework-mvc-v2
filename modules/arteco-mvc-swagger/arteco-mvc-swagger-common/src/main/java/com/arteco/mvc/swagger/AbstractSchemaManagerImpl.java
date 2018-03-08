package com.arteco.mvc.swagger;

import com.arteco.mvc.bootstrap.binding.BindingType;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.schema.SchemaManager;
import com.arteco.mvc.swagger.schema.SwaggerDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by rarnau on 31/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public abstract class AbstractSchemaManagerImpl<DESCRIPTOR extends SwaggerDescriptor> implements SchemaManager<DESCRIPTOR> {

    private LinkedHashMap<Class, SchemaDefinition<DESCRIPTOR>> schemas = new LinkedHashMap<>();


    private String locateFreeName(String proposal) {
        int i = 0;
        String suffix;
        String name = proposal;
        while (exists(name)) {
            i++;
            suffix = "$" + i;
            name = proposal + suffix;
        }
        return proposal;
    }

    private boolean exists(String name) {
        MutableObject<Boolean> exist = new MutableObject<>(false);
        this.schemas.forEach((key, ce) -> {
            if (StringUtils.equals(ce.getName(), name)) {
                exist.setValue(true);
            }
        });
        return exist.getValue();
    }


    public Iterator<SchemaDefinition<DESCRIPTOR>> getAllSchemas() {
        LinkedHashMap<Class, SchemaDefinition<DESCRIPTOR>> schemas;
        do {
            schemas = new LinkedHashMap<>(this.schemas);
            schemas.forEach((key, ce) -> {
                if (ce.isDefinable()) {
                    forceDescriptionIntrospection(ce);
                }
            });

        } while (schemas.size() != this.schemas.size());

        List<SchemaDefinition<DESCRIPTOR>> result = new ArrayList<>();
        this.schemas.forEach((key, ce) -> {
            if (ce.isDefinable()) {
                result.add(ce);
            }
        });
        return result.iterator();
    }

    protected abstract void forceDescriptionIntrospection(SchemaDefinition<DESCRIPTOR> ce);

    public SchemaDefinition<DESCRIPTOR> getSchemaDefinition(Class type) {
        return getSchemaDefinitionPrivate(new BindingType(type));
    }

    public SchemaDefinition<DESCRIPTOR> getSchemaDefinition(BindingType bindingType) {
        return getSchemaDefinitionPrivate(bindingType);
    }

    private SchemaDefinition<DESCRIPTOR> getSchemaDefinitionPrivate(BindingType bindingType) {
        Class type = bindingType.getType();
        SchemaDefinition<DESCRIPTOR> schemaDefinition = schemas.get(type);
        if (schemaDefinition == null) {
            String name = locateFreeName(type.getSimpleName());
            DESCRIPTOR schemaResolver;
            boolean definable;
            if (bindingType.isCollection()) {
                schemaResolver = newArraySchemaResolver(bindingType, name);
                definable = false;
            } else {
                schemaResolver = newObjectSchemaResolver(bindingType, name);
                definable = true;
            }
            schemaDefinition = new SchemaDefinition<>(name, bindingType, schemaResolver, definable);
            schemas.put(type, schemaDefinition);
        }
        return schemaDefinition;
    }

    protected abstract DESCRIPTOR newArraySchemaResolver(BindingType bindingType, String name);

    protected abstract DESCRIPTOR newObjectSchemaResolver(BindingType bindingType, String name);


    protected void addSchema(Class<?> type, SchemaDefinition<DESCRIPTOR> schemaDefinition) {
        schemaDefinition.setType(new BindingType(type));
        this.schemas.put(type, schemaDefinition);
    }
}
