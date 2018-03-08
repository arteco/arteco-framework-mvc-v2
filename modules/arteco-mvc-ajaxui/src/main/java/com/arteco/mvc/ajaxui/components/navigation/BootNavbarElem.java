package com.arteco.mvc.ajaxui.components.navigation;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.util.ComponentUtils;
import lombok.Getter;
import org.apache.velocity.VelocityContext;

import java.util.List;

@Getter
public class BootNavbarElem implements Elem {

    private List<LinkElem> links;
    private String brand;
    private BootMenuBtn menuButton;

    public BootNavbarElem(String brand, LinkElem... links) {
        this.brand = brand;
        this.links = ComponentUtils.append(links);
    }

    @Override
    public String render() {
        VelocityContext context = new VelocityContext();
        context.put("elem", this);
        return ComponentUtils.renderVelocity(this, context);
    }

    public BootNavbarElem menuButton(BootMenuBtn menuButton) {
        this.menuButton = menuButton;
        return this;
    }
}
