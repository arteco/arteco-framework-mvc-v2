package com.arteco.mvc.bootstrap.i18n;

import java.util.Locale;

public interface WebTranslator {

    String translate(Locale locale, String key, Object[] args);
}
