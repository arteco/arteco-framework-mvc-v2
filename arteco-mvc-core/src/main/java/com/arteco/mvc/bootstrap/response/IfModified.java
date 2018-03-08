package com.arteco.mvc.bootstrap.response;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebResponse;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by rarnau on 1/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class IfModified<T, K extends WebResponse<T>> implements WebResponse<T> {


    private final Supplier<K> supplier;
    private final long millis;

    public IfModified(long millis, Supplier<K> supplier) {
        this.millis = DateUtils.truncate(new Date(millis), Calendar.HOUR_OF_DAY).getTime();
        this.supplier = supplier;
    }

    public static <T, K extends WebResponse<T>> IfModified<T, K> ifModified(long instant, Supplier<K> supplier) {
        return new IfModified<>(instant, supplier);
    }

    public static <T, K extends WebResponse<T>> void ifModifiedSince(WebContext webContext, long millis, Consumer<WebContext> supplier) {
        HttpServletRequest request = webContext.getHttpRequest();
        long modifiedSince = request.getDateHeader("If-Modified-Since");
        if (modifiedSince < millis) {
            webContext.getHttpResponse().setDateHeader("Last-Modified", millis);
            supplier.accept(webContext);
        } else {
            webContext.getHttpResponse().setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    @Override
    public void process(WebContext webContext) {
        long millis = this.millis;
        Supplier<K> supplier = this.supplier;
        ifModifiedSince(webContext, millis, webCtx -> supplier.get().process(webCtx));
    }

    @Override
    public T getValue() {
        throw new NotImplementedException("it not should execute");
    }
}
