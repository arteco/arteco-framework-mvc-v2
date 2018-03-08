package com.arteco.mvc.bootstrap.i18n;

import com.arteco.mvc.bootstrap.WebContext;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class HttpHeaderLocaleResolver implements LocaleResolver {

    private Locale defaultLocale;
    private Locale[] availLocales;

    public HttpHeaderLocaleResolver(Locale defaultLocale, Locale[] availLocales) {
        this.defaultLocale = defaultLocale;
        this.availLocales = availLocales;
    }

    @Override
    public Locale resolveLocale(WebContext webContext) {
        Locale targetLocale = defaultLocale;
        HttpServletRequest request = webContext.getHttpRequest();
        Enumeration<Locale> locales = request.getLocales();
        if (locales != null) {
            while (locales.hasMoreElements()) {
                Locale reqLocale = locales.nextElement();
                if (in(reqLocale, availLocales)) {
                    targetLocale = reqLocale;
                    break;
                }
            }
        }
        return targetLocale;
    }

    private boolean in(Locale reqLocale, Locale[] availLocales) {
        if (availLocales != null && reqLocale != null) {
            for (Locale locale : availLocales) {
                if (locale.getLanguage().equals(reqLocale.getLanguage())) {
                    return true;
                }
            }
        }
        return false;
    }
}
