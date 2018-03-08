package com.arteco.mvc.sample.controller.data;

import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.arteco.mvc.bootstrap.binding.Bindings.*;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectRequest extends BindingForm {

    private Integer queryParam;
    private Integer[] queryParams;
    private Integer pathVar;
    private String stringParam;
    private ValidObject body;

    @Override
    public List<Binding> define() {
        return definitions(this,
                fromParam("queryParam").obligatory(true).min(3),
                fromParam("queryParams").min(3),
                fromParam("stringParam").min(3)
                , fromPath("pathVar").min(3)
                , fromBody("body").obligatory(true).valid(true)
        );
    }
}
