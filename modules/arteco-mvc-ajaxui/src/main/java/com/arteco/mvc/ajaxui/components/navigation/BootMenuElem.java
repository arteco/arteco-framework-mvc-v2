package com.arteco.mvc.ajaxui.components.navigation;

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
public class BootMenuElem implements Elem {


    Elem caption;
    LinkElem[] items;
    Elem menu;
    Elem content;
    Elem footer;

    public BootMenuElem(Elem caption, LinkElem... items) {
        this.caption = caption;
        this.items = items;
    }

    @Override
    public String render() {
        VelocityContext context = new VelocityContext();
        context.put("elem", this);
        return ComponentUtils.renderVelocity(this, context);
    }
}
