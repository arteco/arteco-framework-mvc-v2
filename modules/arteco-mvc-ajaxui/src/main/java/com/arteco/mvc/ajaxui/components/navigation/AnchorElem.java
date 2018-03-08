package com.arteco.mvc.ajaxui.components.navigation;

import com.arteco.mvc.ajaxui.components.base.Elem;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@AllArgsConstructor
public class AnchorElem implements Elem {
    Link link;
    Elem caption;

    @Override
    public String render() {
        return "<a href='" + link.getPath() + "'>" + caption.render() + "</a>";
    }
}
