package com.arteco.mvc.ajaxui.components.navigation;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.text.TextElem;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@AllArgsConstructor
public class LinkElem implements Elem {
    Link link;
    Elem caption;
    String styleClass;

    public LinkElem(Link link, String text) {
        this.link = link;
        this.caption = new TextElem(text);
    }

    public LinkElem(Link link, Elem caption) {
        this.link = link;
        this.caption = caption;
    }

    public static String renderLink(Link link, String caption) {
        return renderLink(link, caption, null);
    }

    public static String renderLink(Link link, String caption, String styleClass) {
        String prefix = StringUtils.EMPTY;
        if (styleClass != null) {
            prefix = " class=\"" + styleClass + "\" ";
        }
        return "<a " + renderLinkAttr(link) + prefix + ">" + caption + "</a>";
    }

    public static String renderLinkAttr(Link link) {
        String finalPath = link.build();
        return renderLinkAttr(finalPath);
    }

    public static String renderLinkAttr(String finalPath) {
        return "href='#" + finalPath + "' data-rd-link='" + finalPath + "'";
    }

    @Override
    public String render() {
        return renderLink(link, caption.render(), styleClass);
    }

    public LinkElem styleClass(String styleClass) {
        this.styleClass = styleClass;
        return this;
    }
}
