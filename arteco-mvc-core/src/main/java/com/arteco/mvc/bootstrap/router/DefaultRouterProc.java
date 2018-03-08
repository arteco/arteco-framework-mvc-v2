package com.arteco.mvc.bootstrap.router;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.binding.BindingContext;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.chain.WebChain;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;

import java.util.List;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DefaultRouterProc implements WebProcessor {
    @Override
    public void process(WebContext webContext, WebChain chain) throws Exception {
        WebRoute route = webContext.getWebRoute();
        WebFormHandler handler = route.getHandler();

        Class<? extends BindingForm> formType = route.getFormType();
        BindingForm form = null;
        if (formType != null && !formType.equals(BindingForm.class)) {
            form = newFormInstance(formType);
            BindingContext bindingContext = new BindingContext<>(form, webContext);
            List<Binding> definitions = form.define();
            if (definitions != null) {
                for (Binding binding : definitions) {
                    binding.evaluate(bindingContext);
                }
            }
        }
        WebResponse response = (WebResponse) handler.apply(form);
        webContext.setResponse(response);
        chain.process();
    }

    @SuppressWarnings("unchecked")
    private BindingForm newFormInstance(Class<? extends BindingForm> formType) {
        try {
            return formType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw ExceptionUtils.manage(e);
        }
    }
}
