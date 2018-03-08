package com.arteco.mvc.ajaxui.components.form;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.text.TextElem;
import com.arteco.mvc.ajaxui.components.util.ComponentUtils;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarnau on 15/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
public class BootInputElem implements Elem {

    private Elem help;
    private Elem caption;
    private String errorMessage;
    private InputType type = InputType.text;
    private String id = StringUtils.EMPTY;
    private String placeholder = StringUtils.EMPTY;
    private String name = StringUtils.EMPTY;
    private String value = StringUtils.EMPTY;
    private boolean readonly = false;
    private Integer size = 0;

    private Map<String, String> data = new HashMap<>();

    public BootInputElem(Elem caption) {
        this.caption = caption;
    }

    public BootInputElem(String caption) {
        this.caption = new TextElem(caption);
    }

    @Override
    public String render() {
        VelocityContext context = new VelocityContext();
        context.put("elem", this);
        return ComponentUtils.renderVelocity(this, context);
    }

    public BootInputElem id(String id) {
        this.id = id;
        return this;
    }

    public BootInputElem placeholder(String placeholder) {
        this.placeholder = StringUtils.trimToEmpty(placeholder);
        return this;
    }

    public BootInputElem help(Elem help) {
        this.help = help;
        return this;
    }

    public BootInputElem errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public BootInputElem name(String name) {
        this.name = name;
        return this;
    }

    public BootInputElem value(String value) {
        this.value = StringUtils.trimToEmpty(value);
        return this;
    }

    public BootInputElem readonly(boolean readonly) {
        this.readonly = readonly;
        return this;
    }

    public BootInputElem size(Integer size) {
        this.size = size;
        return this;
    }

    public BootInputElem type(InputType type) {
        this.type = type;
        return this;
    }

    public BootInputElem caption(Elem caption) {
        this.caption = caption;
        return this;
    }

    public BootInputElem data(String name, String value) {
        this.data.put(name, value);
        return this;
    }

    public BootInputElem data(Map<String, String> attrs) {
        if (attrs != null) {
            this.data.putAll(attrs);
        }
        return this;
    }
}
