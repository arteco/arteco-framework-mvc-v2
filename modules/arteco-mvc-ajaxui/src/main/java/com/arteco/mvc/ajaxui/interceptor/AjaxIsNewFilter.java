package com.arteco.mvc.ajaxui.interceptor;

import com.arteco.mvc.ajaxui.AjaxUI;
import com.arteco.mvc.ajaxui.base.PageContext;
import com.arteco.mvc.bootstrap.WebApp;
import org.apache.commons.lang3.BooleanUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by rarnau on 9/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class AjaxIsNewFilter implements Filter {

    private final WebApp app;

    public AjaxIsNewFilter(WebApp app) {
        this.app = app;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        boolean isnewreq = BooleanUtils.toBoolean(httpServletRequest.getParameter("_fq"));
        PageContext pageContext = AjaxUI.getOrCreatePageContext(request, response, app);
        pageContext.setInitApp(isnewreq);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
