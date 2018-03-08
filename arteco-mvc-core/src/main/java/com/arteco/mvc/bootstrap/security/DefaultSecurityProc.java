package com.arteco.mvc.bootstrap.security;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.WebResponse;
import com.arteco.mvc.bootstrap.binding.BindingForm;
import com.arteco.mvc.bootstrap.chain.WebChain;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.security.authentication.WebAuthResult;
import com.arteco.mvc.bootstrap.security.authentication.WebAuthenticator;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DefaultSecurityProc implements WebProcessor {
    public static <T, FORM extends BindingForm, RES extends WebResponse<T>> WebRolesAllowed<T, FORM, RES> allowedRoles(String... roles) {
        return new WebRolesAllowed<>(roles);
    }

    @Override
    public void process(WebContext webContext, WebChain chain) throws Exception {
        WebRolesAllowed rolesAllowed = getWebRolesAllowed(webContext);
        if (rolesAllowed != null) {
            WebUser user = webContext.getUser();
            if (user == null) {
                WebAuthenticator authenticator = webContext.getApp().getAuthenticator();
                if (authenticator == null) {
                    throw ExceptionUtils.trigger("Authenticator not provided");
                }
                WebAuthResult action = authenticator.authenticate(webContext);
                if (action == null) {
                    throw ExceptionUtils.trigger("Authenticator can not return null action");
                }
                if (WebAuthResult.TERMINATE.equals(action)) {
                    return;
                }
            }
        }
        boolean allowed = isAllowed(webContext, rolesAllowed);
        if (allowed) {
            chain.process();
        } else {
            throw ExceptionUtils.trigger("No action allowed");
        }
    }

    private WebRolesAllowed getWebRolesAllowed(WebContext webContext) {
        WebRoute route = webContext.getWebRoute();
        WebRolesAllowed result = (WebRolesAllowed) route.getFeatures().get(WebRolesAllowed.class);
        if (result != null && (result.getRoles() == null || result.getRoles().length == 0)) {
            result = null;
        }
        return result;
    }

    private boolean isAllowed(WebContext webContext, WebRolesAllowed rolesAllowed) {
        boolean allowed = true;
        if (rolesAllowed != null) {
            allowed = false;
            WebUser user = webContext.getUser();
            if (user == null) {
                throw ExceptionUtils.trigger("User is not logged");
            }
            for (String role : rolesAllowed.getRoles()) {
                if (user.isInRole(role)) {
                    allowed = true;
                    break;
                }
            }
        }
        return allowed;
    }
}
