package com.arteco.mvc.bootstrap.router;

import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.chain.WebChainRunner;
import com.arteco.mvc.bootstrap.response.view.WebViewEngine;
import com.arteco.mvc.bootstrap.router.feature.WebFeature;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jodah.typetools.TypeResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@NoArgsConstructor
public class WebRoute<T, FORM extends BindingForm, RES extends WebResponse<T>> {

    private Class<?> responseObjectType;
    private Class<? extends BindingForm> formType;
    private Class<? extends WebResponse> responseType;
    private WebFormHandler<T, FORM, RES> handler;

    private WebMethod method = WebMethod.GET;
    private PathExpression path;
    private WebChainRunner chain;
    private WebProcessor errorHandler;
    private WebViewEngine viewEngine;
    private Map<Class<? extends WebFeature>, WebFeature> features = new HashMap<>();


    @SuppressWarnings("unchecked")
    public WebRoute(WebFormHandler<T, FORM, RES> handler) {
        Class<?>[] classes = TypeResolver.resolveRawArguments(Function.class, handler.getClass());
        this.responseType = (Class<? extends WebResponse>) classes[1];
        this.formType = (Class<? extends BindingForm>) classes[0];
        this.handler = handler;
    }

    public WebRoute<T, FORM, RES> method(final WebMethod verb) {
        this.method = verb;
        return this;
    }

    public WebRoute<T, FORM, RES> path(final String path) {
        this.path = new PathExpression(path);
        return this;
    }

    public WebRoute<T, FORM, RES> chain(final WebChainRunner chain) {
        this.chain = chain;
        return this;
    }

    public WebRoute<T, FORM, RES> viewEngine(final WebViewEngine viewEngine) {
        this.viewEngine = viewEngine;
        return this;
    }

    public Map<String, String> canApply(WebMethod verb, String uri) {
        Map<String, String> res = null;
        if (this.method == null || this.method.equals(verb)) {
            res = path.match(uri);
        }
        return res;
    }

    private void putFeature(WebFeature feature) {
        Class<? extends WebFeature> clazz = feature.getClass();
        this.features.put(clazz, feature);
    }

    @SuppressWarnings("unchecked")
    public <K extends WebFeature<T, FORM, RES>> K getFeature(Class<K> featureClass) {
        return (K) features.get(featureClass);
    }


    public WebRoute<T, FORM, RES> feature(WebFeature<T, FORM, RES> feature) {
        putFeature(feature);
        feature.complete(this);
        return this;
    }

    public Class<? extends BindingForm> getFormType() {
        return formType;
    }
}
