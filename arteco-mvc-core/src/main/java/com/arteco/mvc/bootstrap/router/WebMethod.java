package com.arteco.mvc.bootstrap.router;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public enum WebMethod {
    GET, POST;

    public boolean is(String method) {
        return StringUtils.endsWithIgnoreCase(this.name(), method);
    }
}
