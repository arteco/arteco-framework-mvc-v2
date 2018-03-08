package com.arteco.mvc.bootstrap.security.authentication;

import com.arteco.mvc.bootstrap.WebContext;

public interface WebAuthenticator {
    /**
     * Debe tratar de autenticar el usuario mediante los datos existentes en request.
     * Si la autenticación tiene éxito debe guardarlo en WebContext. Si no, debe
     * modificar la respuesta (p.e. un redirect) y responder con un result TERMINATE.
     */
    WebAuthResult authenticate(WebContext webContext);

}
