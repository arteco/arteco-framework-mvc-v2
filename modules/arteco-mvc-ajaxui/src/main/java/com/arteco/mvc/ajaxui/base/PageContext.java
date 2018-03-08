package com.arteco.mvc.ajaxui.base;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.util.Locale;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@Slf4j
public class PageContext {


    private final WebApp webApp;
    private final WebContext webContext;
    private final StringBuilder stringBuilder;
    private boolean initApp;

    public PageContext(WebContext webContext) {
        this.webContext = webContext;
        this.webApp = webContext.getApp();
        this.stringBuilder = new StringBuilder();
    }

    public PageContext append(String content) {
        stringBuilder.append(content);
        return this;
    }

    public PageContext append(Elem content) {
        stringBuilder.append(content.render());
        return this;
    }

    public String render() {
        String result = stringBuilder.toString();
        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment("<response>" + result + "</response>");
        result = doc.body().html();
        return result;
    }

    public String getMessage(String key, Object[] args) {
        Locale locale = webContext.getLocale();
        return webApp.getTranslator().translate(locale, key, args);
    }


}
