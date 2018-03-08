package com.arteco.mvc.bootstrap.binding;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.error.exception.WebParseException;
import com.arteco.mvc.bootstrap.response.json.JsonEngine;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public enum BindingScope {
    PATH {
        @Override
        public <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, String name, BindingType type) {
            return bindingContext.getWebContext().getPathVariables().get(name);
        }
    },
    QUERY {
        @Override
        public <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, String name, BindingType type) {
            return bindingContext.getWebContext().getHttpRequest().getParameterValues(name);
        }
    },
    BODY {
        @Override
        public <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, String name, BindingType type) {
            try {
                WebContext webContext = bindingContext.getWebContext();
                JsonEngine jsonEngine = webContext.getApp().getJsonEngine();
                return jsonEngine.fromJson(webContext.getHttpRequest().getInputStream(), type.getType());
            } catch (WebParseException e) {
                log.warn("Error reading json from field {} with description {}", name, e.getLocalizedMessage());
                return null;
            } catch (IOException e) {
                throw ExceptionUtils.manage(e);
            }
        }
    },
    HEADER {
        @Override
        public <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, String name, BindingType type) {
            return bindingContext.getWebContext().getHttpRequest().getHeader(name);
        }
    },
    COOKIE {
        @Override
        public <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, String name, BindingType type) {
            Cookie[] cookies = bindingContext.getWebContext().getHttpRequest().getCookies();
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
            return null;
        }
    };

    public abstract <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, String name, BindingType type);

    public <FORM extends BindingForm> Object apply(BindingContext<FORM> bindingContext, Binding binding) {
        return apply(bindingContext, binding.getName(), bindingContext.getType(binding));
    }
}
