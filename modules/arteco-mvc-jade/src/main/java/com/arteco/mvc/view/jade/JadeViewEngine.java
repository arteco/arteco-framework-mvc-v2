package com.arteco.mvc.view.jade;

import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.response.view.ViewWebResponse;
import com.arteco.mvc.bootstrap.response.view.WebViewEngine;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JadeViewEngine implements WebViewEngine {

    private JadeConfiguration config;

    public JadeViewEngine() {
        config = new JadeConfiguration();
        config.setTemplateLoader(new ClasspathTemplateLoader());
    }

    @Override
    public void process(WebContext webContext, ViewWebResponse viewWebResponse) {
        try {
            String view = viewWebResponse.getViewName();
            JadeTemplate template = config.getTemplate(view);
            Map<String, Object> params = new HashMap<>();

            Locale locale = webContext.getLocale();
            HttpServletRequest httpReq = webContext.getHttpRequest();
            HttpServletResponse httpRes = webContext.getHttpResponse();

            params.put("httpServletRequest", httpReq);
            params.put("httpServletResponse", httpRes);
            params.put("view", view);
            params.put("locale", locale);
            params.putAll(viewWebResponse.getAttributes());

            String html = config.renderTemplate(template, params);
            httpRes.setContentLength(html.length());
            httpRes.setContentType(MimeType.HTML.getMimeType());
            IOUtils.write(html, httpRes.getOutputStream(), "utf-8");
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }
}
