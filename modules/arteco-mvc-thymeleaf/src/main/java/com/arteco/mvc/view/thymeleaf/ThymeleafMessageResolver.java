package com.arteco.mvc.view.thymeleaf;


import com.arteco.mvc.bootstrap.WebApp;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.messageresolver.AbstractMessageResolver;

import java.util.Locale;

/**
 * Created by rarnau on 13/11/16.
 * Arteco Consulting Sl.
 * mailto: info@arteco-consulting.com
 */
@Slf4j
public class ThymeleafMessageResolver extends AbstractMessageResolver {


    private final WebApp app;

    public ThymeleafMessageResolver(WebApp app) {
        this.app = app;
    }


    @Override
    public String resolveMessage(ITemplateContext context, Class<?> aClass, String key, Object[] messageParameters) {
        Locale locale = context.getLocale();
        return app.getTranslator().translate(locale, key, messageParameters);
    }

    @Override
    public String createAbsentMessageRepresentation(ITemplateContext iTemplateContext, Class<?> aClass, String key, Object[] objects) {
        return "¿¿¿" + key + "???";
    }
}
