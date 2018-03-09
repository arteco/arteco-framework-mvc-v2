package com.arteco.mvc.ajaxui;

import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.WebController;
import com.arteco.mvc.bootstrap.response.Responses;
import com.arteco.mvc.bootstrap.response.txt.Replacement;
import com.arteco.mvc.bootstrap.router.RouterRegister;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by rarnau on 9/3/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class AjaxUiController implements WebController {
    private final WebApp webapp;
    private final String prefix;
    private final String homePage;

    public AjaxUiController(WebApp webApp, String subContextPath, String homePage) {
        this.webapp = webApp;
        this.prefix = subContextPath;
        this.homePage = homePage;
        if (!StringUtils.startsWith(prefix, "/")) {
            throw new IllegalArgumentException("[subContextPath] Path should start with '/'");
        }
        if (!StringUtils.startsWith(homePage, "/")) {
            throw new IllegalArgumentException("[homePage] should start with '/'");
        }
    }


    @Override
    public void registerPaths(RouterRegister paths) {
        paths.add(prefix, () -> Responses.redirect("ajaxui/index.html"));
        paths.add(prefix + "/", () -> Responses.redirect("ajaxui/index.html"));

        paths.add(prefix + "/app.css", () -> Responses.file("static/ajaxui/app.css", MimeType.CSS));
        paths.add(prefix + "/vendor.js", () -> Responses.file("static/ajaxui/vendor.js", MimeType.JS));
        paths.add(prefix + "/index.html", () -> Responses.file("static/ajaxui/index.html", MimeType.HTML));
        paths.add(prefix + "/app.js", () -> Responses.file("static/ajaxui/app.js", MimeType.JS,
                new Replacement("%CONTEXT_PATH%", prefix),
                new Replacement("%HOME_PAGE%", homePage)
        ));
    }
}
