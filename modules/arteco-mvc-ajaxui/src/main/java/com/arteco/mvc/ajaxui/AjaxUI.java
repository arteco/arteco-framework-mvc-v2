package com.arteco.mvc.ajaxui;

import com.arteco.mvc.ajaxui.base.PageContext;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebMvc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by rarnau on 5/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class AjaxUI {
    public static PageContext getOrCreatePageContext(ServletRequest request, ServletResponse response, WebApp app) {
        WebContext context = WebMvc.getOrCreateContext(request, response, app);
        PageContext page = context.getAttribute(PageContext.class);
        if (page == null) {
            page = new PageContext(context);
            context.setAttribute(page);
        }
        return page;
    }
}
