package com.arteco.mvc.bootstrap.utils;

import com.arteco.mvc.bootstrap.binding.BindingType;
import net.jodah.typetools.TypeResolver;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ObjectUtils {
    public static BindingType getType(Object root, String propertyName) {
        try {
            Field field = FieldUtils.getField(root.getClass(), propertyName);
            return new BindingType(field);
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }

    }

    public static BindingType getType(Class targetClass, String propertyName) {
        try {
            Field field = FieldUtils.getField(targetClass, propertyName, true);
            return new BindingType(field);
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }

    }


    public static Object getPropertyValue(Object root, String name) {
        try {
            return PropertyUtils.getProperty(root, name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw ExceptionUtils.manage(e);
        }
    }


    public static void setPropertyValue(Object form, String name, Object value) {
        try {
            PropertyUtils.setProperty(form, name, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    public static Class<?>[] resolveRawArguments(Class<?> type, Class<?> subtype) {
        return TypeResolver.resolveRawArguments(type, subtype);
    }


    public static Field[] getFields(Class targetClass) {
        try {
            return FieldUtils.getAllFields(targetClass);
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }

    public static Class toWrapperClass(Class type) {
        if (type == null || !type.isPrimitive()) {
            return type;
        }
        return ClassUtils.primitiveToWrapper(type);
    }
}
