package com.arteco.mvc.bootstrap;

import com.arteco.mvc.bootstrap.chain.DefaultChainRunner;
import com.arteco.mvc.bootstrap.chain.WebChainRunner;
import com.arteco.mvc.bootstrap.converter.Converters;
import com.arteco.mvc.bootstrap.converter.WebConversion;
import com.arteco.mvc.bootstrap.database.DatabaseJdbcProcessor;
import com.arteco.mvc.bootstrap.error.DefaultExceptionManager;
import com.arteco.mvc.bootstrap.error.DefaultExceptionProc;
import com.arteco.mvc.bootstrap.error.DefaultNotFoundProc;
import com.arteco.mvc.bootstrap.i18n.DefaultI18NProcessor;
import com.arteco.mvc.bootstrap.i18n.DefaultWebTranslator;
import com.arteco.mvc.bootstrap.i18n.WebTranslator;
import com.arteco.mvc.bootstrap.info.WebInfo;
import com.arteco.mvc.bootstrap.response.json.JacksonJsonEngine;
import com.arteco.mvc.bootstrap.response.json.JsonEngine;
import com.arteco.mvc.bootstrap.response.view.DefaultRenderProc;
import com.arteco.mvc.bootstrap.response.view.WebViewEngine;
import com.arteco.mvc.bootstrap.response.yaml.YamlEngine;
import com.arteco.mvc.bootstrap.router.DefaultRouterProc;
import com.arteco.mvc.bootstrap.router.RouterRegister;
import com.arteco.mvc.bootstrap.security.DefaultSecurityProc;
import com.arteco.mvc.bootstrap.security.authentication.WebAuthenticator;
import com.arteco.mvc.bootstrap.servlet.FilterDefinition;
import com.arteco.mvc.bootstrap.servlet.ServletDefinition;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rarnau on 24/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class WebApp {

    private final RouterRegister register = new RouterRegister(this);
    private final WebInfo info = new WebInfo();
    private boolean development;
    private int port = 8080;
    private String host = "localhost";
    private String contextPath = "";

    private WebContainer container;

    private WebProcessor notFound = new DefaultNotFoundProc();

    private WebProcessor i18n = new DefaultI18NProcessor();
    private WebProcessor exceptions = new DefaultExceptionProc();
    private WebProcessor security = new DefaultSecurityProc();
    private WebProcessor rendering = new DefaultRenderProc();
    private WebProcessor routing = new DefaultRouterProc();
    private WebProcessor database = new DatabaseJdbcProcessor();

    private WebAuthenticator authenticator;
    private WebExceptionManager exceptionManager = new DefaultExceptionManager();
    private WebChainRunner chainRunner = new DefaultChainRunner();

    private WebViewEngine viewEngine;
    private JsonEngine jsonEngine = new JacksonJsonEngine();
    private YamlEngine yamlEngine = (JacksonJsonEngine) jsonEngine;


    private WebConversion converters = Converters.getInstance();
    private WebTranslator translator = new DefaultWebTranslator(converters);

    private List<WebProcessor> processors;

    private List<ServletDefinition> servlets = new ArrayList<>();
    private List<FilterDefinition> filters = new ArrayList<>();

    public void start() {
        initProcessors();
        initContainer();
        container.init(this);
        container.registerFilters();
        container.registerServlets();
        container.registerDefaultServlet();
        container.start();
    }

    private void initContainer() {
        if (container == null) {
            throw ExceptionUtils.trigger("Servlet container is not setted!");
        }
    }

    private void initProcessors() {
        processors = Arrays.asList(
                i18n,
                exceptions,
                security,
                database,
                rendering,
                routing
        );
    }

    public WebApp addModule(WebModule module) {
        module.configure(this);
        return this;
    }


    public WebApp addController(WebController controller) {
        controller.registerPaths(register);
        return this;
    }

    public WebApp port(final int port) {
        this.port = port;
        return this;
    }

    public WebApp host(final String host) {
        this.host = host;
        return this;
    }

    public WebApp contextPath(final String contextPath) {
        if (!StringUtils.startsWith(contextPath, "/")) {
            throw ExceptionUtils.trigger("Context path should start with '/'");
        }
        this.contextPath = contextPath;
        return this;
    }

    public WebApp name(final String name) {
        this.getInfo().setName(name);
        return this;
    }

    public WebApp notFound(final WebProcessor notFound) {
        this.notFound = notFound;
        return this;
    }

    public WebApp i18n(final WebProcessor i18n) {
        this.i18n = i18n;
        return this;
    }

    public WebApp exceptions(final WebProcessor exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public WebApp security(final WebProcessor security) {
        this.security = security;
        return this;
    }

    public WebApp rendering(final WebProcessor rendering) {
        this.rendering = rendering;
        return this;
    }

    public WebApp routing(final WebProcessor routing) {
        this.routing = routing;
        return this;
    }

    public WebApp chainRunner(final WebChainRunner chainRunner) {
        this.chainRunner = chainRunner;
        return this;
    }

    public WebApp viewEngine(final WebViewEngine viewEngine) {
        this.viewEngine = viewEngine;
        return this;
    }

    public WebApp processors(final List<WebProcessor> processors) {
        this.processors = processors;
        return this;
    }

    public WebApp authenticator(final WebAuthenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public WebApp addServlet(final ServletDefinition definition) {
        this.servlets.add(definition);
        return this;
    }

    public WebApp addFilter(final FilterDefinition definition) {
        this.filters.add(definition);
        return this;
    }
}
