package com.arteco.mvc.view.thymeleaf;

import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.response.view.ViewWebResponse;
import com.arteco.mvc.bootstrap.response.view.WebViewEngine;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.bootstrap.utils.SeoUtils;
import ognl.OgnlRuntime;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Locale;

public class ThymeleafViewEngine implements WebViewEngine {


    private TemplateEngine templateEngine = new TemplateEngine();
    private ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    public ThymeleafViewEngine(WebApp app) {
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setMessageResolver(new ThymeleafMessageResolver(app));
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setCacheable(true);
        OgnlRuntime.setSecurityManager(null);
    }


    private void propagateAttributes(AbstractContext context, HttpServletRequest httpReq) {
        Enumeration enumer = httpReq.getAttributeNames();
        while (enumer.hasMoreElements()) {
            Object name = enumer.nextElement();
            context.setVariable((String) name, httpReq.getAttribute((String) name));
        }
        context.setVariable("seoUtils", new SeoUtils());
    }


    @Override
    public void process(WebContext webContext, ViewWebResponse viewWebResponse) {
        try {
            String view = viewWebResponse.getViewName();
            if (view == null) {
                return;
            }
            HttpServletResponse httpRes = webContext.getHttpResponse();
            HttpServletRequest httpReq = webContext.getHttpRequest();
            ServletContext servletContext = httpReq.getServletContext();
            Locale locale = webContext.getLocale();

            httpRes.setContentType(MimeType.HTML.getMimeType());
            httpRes.setCharacterEncoding("utf-8");
            if (webContext.getApp().isDevelopment()) {
                templateResolver.setCacheable(false);
            }
            org.thymeleaf.context.WebContext context = new org.thymeleaf.context.WebContext(httpReq, httpRes, servletContext, locale);
            propagateAttributes(context, httpReq);
            this.templateEngine.process(view, context, httpRes.getWriter());
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }

}
