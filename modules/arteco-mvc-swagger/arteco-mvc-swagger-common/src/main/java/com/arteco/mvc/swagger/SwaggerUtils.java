package com.arteco.mvc.swagger;

import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class SwaggerUtils {
    public static List<Binding> getBindings(WebRoute route) {
        try {
            List<Binding> bindings = null;
            @SuppressWarnings("unchecked")
            Class<? extends BindingForm> formClass = route.getFormType();
            if (formClass != null && !BindingForm.class.equals(formClass)) {
                BindingForm form = formClass.newInstance();
                bindings = form.define();
            }
            return bindings;
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }

    public static boolean shouldInclude(Field f) {
        return null == f.getAnnotation(JsonIgnore.class) && !Modifier.isStatic(f.getModifiers());
    }
}
