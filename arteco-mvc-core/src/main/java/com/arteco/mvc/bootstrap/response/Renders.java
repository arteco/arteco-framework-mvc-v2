package com.arteco.mvc.bootstrap.response;

import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.response.txt.Replacement;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class Renders {
    private static void serveContent(WebContext webContext, byte[] bytes, MimeType mime) {
        try {
            HttpServletResponse response = webContext.getHttpResponse();
            response.setContentType(mime.getMimeType());
            response.setContentLength(bytes.length);
            response.setCharacterEncoding("utf-8");
            ServletOutputStream oos = response.getOutputStream();
            oos.write(bytes);
            oos.flush();
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }

    }

    public static void writeJson(WebContext webContext, String text) {
        byte[] bytes = text.getBytes();
        MimeType mime = MimeType.JSON;
        serveContent(webContext, bytes, mime);
    }

    public static void writeText(WebContext webContext, String text) {
        byte[] bytes = text.getBytes();
        MimeType mime = MimeType.TEXT;
        serveContent(webContext, bytes, mime);
    }

    public static void writeCss(WebContext webContext, String css) {
        byte[] bytes = css.getBytes();
        MimeType mime = MimeType.CSS;
        serveContent(webContext, bytes, mime);
    }

    public static void writeYaml(WebContext webContext, String text) {
        byte[] bytes = text.getBytes();
        MimeType mime = MimeType.YAML;
        serveContent(webContext, bytes, mime);
    }

    public static void writeHtml(WebContext webContext, String text) {
        byte[] bytes = text.getBytes();
        MimeType mime = MimeType.HTML;
        serveContent(webContext, bytes, mime);
    }

    public static void writeJs(WebContext webContext, String text) {
        byte[] bytes = text.getBytes();
        MimeType mime = MimeType.JS;
        serveContent(webContext, bytes, mime);
    }

    public static void write(WebContext webContext, String text, MimeType mime, Replacement... replacements) {
        if (replacements != null) {
            for (Replacement replacement : replacements) {
                text = StringUtils.replace(text, replacement.getSource(), replacement.getTarget());
            }
        }
        byte[] bytes = text.getBytes();
        serveContent(webContext, bytes, mime);
    }
}
