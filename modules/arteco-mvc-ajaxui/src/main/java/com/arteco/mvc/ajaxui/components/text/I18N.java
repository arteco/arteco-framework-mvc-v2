package com.arteco.mvc.ajaxui.components.text;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.bootstrap.WebThread;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@Slf4j
public class I18N implements Elem {
    private String key;
    private Object[] args;

    public I18N(String key) {
        this.key = key;
    }

    public I18N(String key, Object[] args) {
        this.key = key;
        this.args = args;
    }

    @Override
    public String render() {
        return WebThread.get().getMessage(key, args);
    }
}
