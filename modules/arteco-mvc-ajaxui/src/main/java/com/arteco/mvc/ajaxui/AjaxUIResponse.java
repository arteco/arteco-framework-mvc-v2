package com.arteco.mvc.ajaxui;

import com.arteco.mvc.ajaxui.base.PageContext;
import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.response.Renders;

/**
 * Created by rarnau on 5/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class AjaxUIResponse implements WebResponse<String> {

    private final Elem root;

    public AjaxUIResponse(Elem elem) {
        this.root = elem;
    }

    public static AjaxUIResponse ajaxUI(Elem elem) {
        return new AjaxUIResponse(elem);
    }

    @Override
    public void process(WebContext webContext) {
        PageContext context = webContext.getAttribute(PageContext.class);
        context.append(root);
        String html = context.render();
        Renders.writeHtml(webContext, html);
    }
}
