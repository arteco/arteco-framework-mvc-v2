package com.arteco.mvc.ajaxui.components.form;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.text.TextElem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarnau on 5/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDescriptor implements Comparable<FieldDescriptor> {

    private final static Comparator<FieldDescriptor> comparator =
            Comparator.comparing(FieldDescriptor::getOrder)
                    .thenComparing(FieldDescriptor::getName);


    InputType type = InputType.text;
    Integer order = Integer.MAX_VALUE;
    boolean skip = false;

    Elem caption;
    String name;

    String value;
    String help;
    String placeholder;
    String id;
    String errorMessage;
    boolean readonly;
    boolean hidden;
    Integer size;

    Map<String, String> data = new HashMap<>();


    @Override
    public int compareTo(FieldDescriptor o) {
        return comparator.compare(this, o);
    }


    public Elem getInput() {
        if (!hidden) {
            BootInputElem input = new BootInputElem(caption)
                    .name(name)
                    .caption(caption)
                    .value(value)
                    .readonly(readonly)
                    .type(type)
                    .size(size)
                    .data(data)
                    .placeholder(placeholder)
                    .errorMessage(errorMessage);
            if (help != null) {
                input.help(new TextElem(help));
            }
            return input;
        } else {
            return new TextElem("<input type='hidden' name='" + name + "' value='" + value + "'/>");
        }
    }
}