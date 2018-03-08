package com.arteco.mvc.bootstrap.binding;

import com.arteco.mvc.bootstrap.binding.annotation.WebCollection;
import com.arteco.mvc.bootstrap.binding.annotation.WebMap;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.bootstrap.utils.ObjectUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Data
public class BindingType {
    Field field;
    Class type;
    Class componentType;
    Class keyType;

    public BindingType(Field field) {
        this(field.getType());
        this.field = field;
        completeField(field);
    }

    public BindingType(Class<?> type) {
        if (type.isArray()) {
            this.type = Collection.class;
            this.componentType = ObjectUtils.toWrapperClass(type.getComponentType());
        }
        this.type = ObjectUtils.toWrapperClass(type);
    }

    private void completeField(Field field) {
        if (Collection.class.isAssignableFrom(type)) {
            WebCollection ann = field.getAnnotation(WebCollection.class);
            complete(ann);
        }
        if (Map.class.isAssignableFrom(type)) {
            WebMap ann = field.getAnnotation(WebMap.class);
            complete(ann);
        }
    }

    private void complete(WebCollection ann) {
        if (ann == null) {
            throw ExceptionUtils.trigger("@WebCollection is obligatory for fields of type Collection on " + field);
        }
        type = Collection.class;
        componentType = ann.type();
    }

    private void complete(WebMap ann) {
        if (ann == null) {
            throw ExceptionUtils.trigger("@WebMap is obligatory for fields of type Map on " + field);
        }
        type = Map.class;
        componentType = ann.type();
        keyType = ann.keyType();
    }

    public boolean isCollection() {
        return componentType != null;
    }

    public boolean isAssignableFrom(Class aClass) {
        return type.isAssignableFrom(aClass);
    }
}
