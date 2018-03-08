package com.arteco.mvc.ajaxui.components.text;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.util.ComponentUtils;
import lombok.Getter;
import org.apache.velocity.VelocityContext;

/**
 * Created by rarnau on 15/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
public class TemplateElem implements Elem {

    private String template;

    public TemplateElem(String template) {
        this.template = template;
    }

    @Override
    public String render() {
        VelocityContext context = new VelocityContext();
        context.put("elem", this);
        return ComponentUtils.renderVelocity(template, context);
    }

}
