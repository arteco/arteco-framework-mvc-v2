package com.arteco.mvc.bootstrap.response;


import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceUtils;
import com.arteco.mvc.bootstrap.response.json.JsonWebResponse;
import com.arteco.mvc.bootstrap.response.view.HtmlWebResponse;
import com.arteco.mvc.bootstrap.response.view.TextWebResponse;
import com.arteco.mvc.bootstrap.response.view.ViewWebResponse;
import com.arteco.mvc.bootstrap.response.yaml.YamlWebResponse;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class Responses {
    public static ViewWebResponse view(String viewName) {
        return new ViewWebResponse(viewName);
    }

    public static HtmlWebResponse html(String html) {
        return new HtmlWebResponse(html);
    }

    public static <T> YamlWebResponse<T> yaml(T object) {
        return new YamlWebResponse<>(object);
    }

    public static TextWebResponse text(String text) {
        return new TextWebResponse(text);
    }

    public static <T> JsonWebResponse<T> json(T object) {
        return new JsonWebResponse<>(object);
    }

    public static <T> JsonWebResponse<T> json(T object, int status) {
        return new JsonWebResponse<>(object, status);
    }


    public static RedirectWebResponse redirect(String redirection) {
        return new RedirectWebResponse(redirection);
    }

    public static WebResponse<String> file(String path, MimeType mimeType) {
        try {
            WebResource resource = WebResourceUtils.getResource(path);
            String content = IOUtils.toString(resource.getInputStream(), "utf-8");
            return webContext -> Renders.write(webContext, content, mimeType);
        } catch (IOException e) {
            throw ExceptionUtils.manage(e);
        }
    }
}
