package com.arteco.mvc.sample.controller;


import com.arteco.mvc.bootstrap.WebController;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.WebThread;
import com.arteco.mvc.bootstrap.binding.BindingResult;
import com.arteco.mvc.bootstrap.database.DatabaseUtils;
import com.arteco.mvc.bootstrap.resource.WebResourceUtils;
import com.arteco.mvc.bootstrap.resource.loader.WebResourceListLoader;
import com.arteco.mvc.bootstrap.response.IfModified;
import com.arteco.mvc.bootstrap.response.RedirectWebResponse;
import com.arteco.mvc.bootstrap.response.json.JsonWebResponse;
import com.arteco.mvc.bootstrap.response.view.HtmlWebResponse;
import com.arteco.mvc.bootstrap.response.view.TextWebResponse;
import com.arteco.mvc.bootstrap.router.RouterRegister;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.closurejs.ClosureJsResponse;
import com.arteco.mvc.sample.controller.data.ObjectRequest;
import com.arteco.mvc.sample.controller.data.ObjectResponse;
import com.arteco.mvc.sample.domain.MyUser;
import com.arteco.mvc.sample.dto.User;
import com.arteco.mvc.sample.service.MyUserService;
import com.arteco.mvc.sass.SassResponse;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static com.arteco.mvc.bootstrap.database.MappingUtils.map;
import static com.arteco.mvc.bootstrap.response.IfModified.ifModified;
import static com.arteco.mvc.bootstrap.response.Responses.*;
import static com.arteco.mvc.bootstrap.security.DefaultSecurityProc.allowedRoles;
import static com.arteco.mvc.closurejs.ClosureJsWebModule.closureJs;
import static com.arteco.mvc.sass.SassResponse.sass;
import static com.arteco.mvc.swagger.SwaggerWebModule.swagger;

/**
 * Created by rarnau on 10/11/16.
 * Arteco Consulting Sl.
 * mailto: info@arteco-consulting.com
 */
public class IndexController implements WebController {


    private long startTime = System.currentTimeMillis();
    private String contextPath;

    private MyUserService myUserService = new MyUserService();

    @Override
    public void registerPaths(RouterRegister paths) {
        this.contextPath = paths.getWebApp().getContextPath();

        paths.add("/", this::index);
        paths.add("/redirect", this::redirection);
        paths.add("/redirect_success", this::redirectTarget);
        paths.add("/throws", this::throwable);
        paths.add("/js/mainbundle.js", this::serveJS);
        paths.add("/css/mainbundle.css", this::serveCSS);

        paths.add("/protected", this::protectedAction)
                .feature(allowedRoles("ADMIN"));

        paths.add("/json", this::jsonRequest)
                .feature(swagger(ObjectResponse.class, "Simple json response"));

        paths.add("/modified", this::modifiedIfMethod)
                .feature(swagger(ObjectResponse.class, "Cacheable resource"));

        paths.add("/binding", this::binding)
                .feature(swagger(ObjectRequest.class, "Simple json response"));

        paths.add("/binding/{pathVar}", this::binding)
                .feature(swagger(ObjectRequest.class, "Simple json response with path var"));

        paths.add("/binding/{pathVar}", this::binding)
                .feature(swagger(ObjectRequest.class, "Simple json response with path var and post"));

        paths.add("/api/user", this::selectUsers)
                .feature(swagger(User[].class, "List users from db"));

        paths.add("/api/user/querydsl", this::selectUsersQdsl)
                .feature(swagger(User[].class, "List users from db using queryDsl"));

        paths.add("/api/rollback", this::rollBack);

    }

    private TextWebResponse rollBack() {
        Connection conn = WebThread.get().getJdbcConnection();
        try {
            int rows = conn.prepareStatement("INSERT INTO MyUser(username,password) VALUES('newuser','newpass')").executeUpdate();
            if (rows > 0) {
                throw new IllegalArgumentException("Rolling back! Check database, no one user should be appended");
            }
        } catch (SQLException e) {
            throw ExceptionUtils.manage(e);
        }

        return null;
    }


    private IfModified<User[], JsonWebResponse<User[]>> selectUsers() {

        List<User> users = DatabaseUtils.query("select * from MyUser order by username",
                (rs) -> new User(rs.getString("username")));

        return ifModified(startTime, () ->
                json(users.toArray(new User[users.size()])));
    }

    private IfModified<User[], JsonWebResponse<User[]>> selectUsersQdsl() {
        return ifModified(startTime, () -> {
            List<MyUser> users = myUserService.getUsers();
            List<User> usersArr = map(users, User::new);
            return json(usersArr.toArray(new User[users.size()]));
        });
    }

    private ClosureJsResponse serveJS() {
        return closureJs(new WebResourceListLoader(
                WebResourceUtils.getResource("/static/js/library.js"),
                WebResourceUtils.getResource("/static/js/main.js")
        ));
    }

    private SassResponse serveCSS() {
        return sass("/static/sass", "main.scss");
    }

    private HtmlWebResponse index() {
        String title = "";
        title += "<html><body>";
        title += "<h1>Hello world</h1>";
        title += "<ul>";
        title += "<li><a href='" + contextPath + "/json'>/json</a></li>";
        title += "<li><a href='" + contextPath + "/js/mainbundle.js'>/js/mainbundle.js</a></li>";
        title += "<li><a href='" + contextPath + "/css/mainbundle.css'>/css/mainbundle.css</a></li>";
        title += "<li><a href='" + contextPath + "/redirect'>/redirect</a></li>";
        title += "<li><a href='" + contextPath + "/throws'>/throws</a></li>";
        title += "<li><a href='" + contextPath + "/binding'>/binding</a></li>";
        title += "<li><a href='" + contextPath + "/binding/pathvar1'>/binding/pathvar1</a></li>";
        title += "<li><a href='" + contextPath + "/protected'>/protected</a></li>";
        title += "<li><a href='" + contextPath + "/modified'>/modified</a></li>";
        title += "<li><a href='" + contextPath + "/v2/api-docs'>/v2/api-docs</a></li>";
        title += "<li><a href='" + contextPath + "/v3/api-docs'>/v3/api-docs</a></li>";
        title += "<li><a href='" + contextPath + "/api/user'>/api/user</a></li>";
        title += "<li><a href='" + contextPath + "/api/user/querydsl'>/api/user/querydsl</a></li>";
        title += "<li><a href='" + contextPath + "/api/rollback'>/api/rollback</a></li>";
        title += "</ul>";
        title += "</body><html>";
        return html(title);
    }

    private RedirectWebResponse redirection() {
        return redirect("/redirect_success");
    }

    private TextWebResponse redirectTarget() {
        return text("Redirect success");
    }

    private WebResponse<Void> throwable() {
        throw new IllegalArgumentException("Hi hi");
    }

    private JsonWebResponse<ObjectResponse> protectedAction() {
        ObjectResponse jsonObject = new ObjectResponse();
        jsonObject.setMessage("Ok!");
        jsonObject.setTime(Instant.now());
        return json(jsonObject);
    }

    private JsonWebResponse<ObjectResponse> jsonRequest() {
        ObjectResponse jsonObject = new ObjectResponse();
        jsonObject.setMessage("Hello world");
        jsonObject.setTime(Instant.now());
        return json(jsonObject);
    }

    private JsonWebResponse<ObjectRequest> binding(ObjectRequest value) {
        BindingResult result = value.getBindingResult();
        if (!result.hasErrors()) {
            return json(value);
        } else {
            return json(value, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private IfModified<ObjectResponse, JsonWebResponse<ObjectResponse>> modifiedIfMethod() {
        return ifModified(startTime, () -> json(new ObjectResponse("If-Modified-Since caching method", Instant.ofEpochMilli(startTime))));
    }


}
