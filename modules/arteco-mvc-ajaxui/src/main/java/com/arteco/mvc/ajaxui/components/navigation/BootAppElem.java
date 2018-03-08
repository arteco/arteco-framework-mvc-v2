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
public class BootAppElem implements Elem {

    public static final String CONTENT = "content";
    public static final String MENU = "menu";
    public static final String FOOTER = "footer";
    public static final String APP = "app";
    public static final String TOP = "top";

    String brand;
    Elem navBar;
    Elem menu;
    Elem content;
    Elem footer;

    public BootAppElem(String brand, LinkElem[] navLinks, Elem menu, Elem content, Elem footer) {
        this.brand = brand;
        this.navBar = new BootNavbarElem(brand, navLinks)
                .menuButton(new BootMenuBtn("menuButton", "toggleMenu"));
        this.menu = menu;
        this.content = content;
        this.footer = footer;
    }

    @Override
    public String render() {
        VelocityContext context = new VelocityContext();
        context.put("elem", this);
        return ComponentUtils.renderVelocity(this, context);
    }
}
