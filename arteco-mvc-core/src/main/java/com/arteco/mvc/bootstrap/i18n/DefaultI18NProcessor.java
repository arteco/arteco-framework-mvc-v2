package com.arteco.mvc.bootstrap.i18n;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.chain.WebChain;
import lombok.Data;

import java.util.Locale;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class DefaultI18NProcessor implements WebProcessor {
    private final Locale defaultLocale;
    private final Locale[] availLocales;
    private final LocaleResolver localeResolver;

    public DefaultI18NProcessor() {
        this(Locale.getDefault(), Locale.getDefault());
    }

    public DefaultI18NProcessor(Locale defaultLocale, Locale... availLocales) {
        this(new HttpHeaderLocaleResolver(defaultLocale, availLocales), defaultLocale, availLocales);
    }

    public DefaultI18NProcessor(LocaleResolver localeResolver, Locale defaultLocale, Locale... availLocales) {
        this.defaultLocale = defaultLocale;
        this.availLocales = availLocales;
        this.localeResolver = localeResolver;
    }


    @Override
    public void process(WebContext webContext, WebChain chain) throws Exception {
        Locale locale = localeResolver.resolveLocale(webContext);
        webContext.setLocale(locale);
        chain.process();
    }
}
