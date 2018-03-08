package com.arteco.mvc.bootstrap.error;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebExceptionManager;
import com.arteco.mvc.bootstrap.response.Renders;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class DefaultExceptionManager implements WebExceptionManager {
    @Override
    public void manage(WebContext webContext, Exception e) {
        log.error(e.getMessage(), e);
        String text = ExceptionUtils.toString(e);
        Renders.writeText(webContext, text);
    }
}
