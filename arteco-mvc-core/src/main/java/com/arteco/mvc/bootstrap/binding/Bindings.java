package com.arteco.mvc.bootstrap.binding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class Bindings {
    public static Binding fromParam(String queryName) {
        return new Binding(queryName, BindingScope.QUERY);
    }

    public static Binding fromPath(String pathName) {
        return new Binding(pathName, BindingScope.PATH).obligatory(true);
    }

    public static Binding fromBody(String attribName) {
        return new Binding(attribName, BindingScope.BODY);
    }

    public static List<Binding> definitions(BindingForm form, Binding... defs) {
        return definitions(form.getClass(), defs);
    }


    public static List<Binding> definitions(Class<? extends BindingForm> formType, Binding... defs) {
        List<Binding> result = new ArrayList<>();
        if (defs != null) {
            for (Binding binding : defs) {
                binding.setFormType(formType);
                result.add(binding);
            }
        }
        return result;
    }
}
