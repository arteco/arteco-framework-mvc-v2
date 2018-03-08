package com.arteco.mvc.ajaxui.components.util;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class ComponentUtils {

    static {
        Velocity.init();
    }

    public static <T extends Elem> List<T> append(List<T> target, T... innerElem) {
        List<T> result = null;
        if (target != null) {
            result = new ArrayList<>(target);
        }
        if (innerElem != null && innerElem.length > 0) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.addAll(Arrays.asList(innerElem));
        }
        return result;
    }

    public static <T extends Elem> List<T> append(T... elem) {
        if (elem != null) {
            return Arrays.asList(elem);
        }
        return null;
    }

    public static String renderNotAllowed() {
        throw new NotImplementedException("Async");
    }

    public static String renderVelocity(Elem elem, VelocityContext context) {
        String templateName = elem.getClass().getSimpleName() + ".vm";
        InputStream is = elem.getClass().getResourceAsStream(templateName);
        return renderVelocity(is, templateName, context);
    }

    public static String renderVelocity(String templateName, VelocityContext context) {
        WebResource resource = WebResourceUtils.getResource(templateName);
        InputStream is = resource.getInputStream();
        return renderVelocity(is, templateName, context);
    }

    private static String renderVelocity(InputStream is, String templateName, VelocityContext context) {
        StringWriter writer = new StringWriter();
        try {
            Velocity.evaluate(context, writer, templateName, new InputStreamReader(is, "utf-8"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return writer.toString();
    }
}
