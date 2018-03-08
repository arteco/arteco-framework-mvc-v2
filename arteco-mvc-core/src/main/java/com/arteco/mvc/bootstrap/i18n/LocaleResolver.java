package com.arteco.mvc.bootstrap.i18n;

import com.arteco.mvc.bootstrap.WebContext;

import java.util.Locale;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface LocaleResolver {
    Locale resolveLocale(WebContext webContext);

}
