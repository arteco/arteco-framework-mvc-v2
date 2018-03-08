package com.arteco.mvc.bootstrap;

import com.arteco.mvc.bootstrap.router.RouterRegister;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebController {
    void registerPaths(RouterRegister paths);
}
