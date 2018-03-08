package com.arteco.mvc.bootstrap.i18n;

import com.arteco.mvc.bootstrap.converter.WebConversion;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Slf4j
public class DefaultWebTranslator implements WebTranslator {

    public static final String MESSAGES = "messages";

    private final String basename;
    private final Map<Locale, ResourceBundle> resourceBundles = new HashMap<>();
    private final WebConversion converter;

    public DefaultWebTranslator(WebConversion converter) {
        this(MESSAGES, converter);
    }

    public DefaultWebTranslator(String basename, WebConversion converter) {
        this.basename = basename;
        this.converter = converter;
    }


    @Override
    public String translate(Locale locale, String key, Object[] args) {
        if (locale == null) {
            throw ExceptionUtils.trigger("No locale available");
        }
        ResourceBundle resourceBundle = getResourceBundle(locale);
        String raw = resolve(key, resourceBundle);
        if (raw == null) {
            raw = notFoundMessage(key, locale);
        }
        raw = resolveArguments(raw, args);
        return raw;
    }

    private String resolveArguments(String raw, Object[] args) {
        if (args != null && args.length > 0) {
            int i = 0;
            for (Object arg : args) {
                if (arg != null) {
                    String value = converter.convert(arg, String.class);
                    raw = StringUtils.replace(raw, "{" + i + "}", value);
                }
            }
        }
        return raw;
    }

    private String notFoundMessage(String key, Locale locale) {
        log.info("Not found message key " + key + " for " + locale);
        return "###" + key + "###";
    }

    private String resolve(String key, ResourceBundle resourceBundle) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return null;
        }
    }

    private ResourceBundle getResourceBundle(Locale locale) {
        try {
            ResourceBundle resourceBundle = resourceBundles.get(locale);
            if (resourceBundle == null) {
                resourceBundle = ResourceBundle.getBundle(basename, locale);
                resourceBundles.put(locale, resourceBundle);
            }
            return resourceBundle;
        } catch (MissingResourceException mre) {
            return null;
        }
    }
}
