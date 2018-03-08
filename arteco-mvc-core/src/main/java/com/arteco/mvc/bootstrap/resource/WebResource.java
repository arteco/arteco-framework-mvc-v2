package com.arteco.mvc.bootstrap.resource;

import java.io.InputStream;

/**
 * Created by rarnau on 1/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebResource {
    InputStream getInputStream();

    String getEncoding();

    String getFilename();

    long getLastModified();
}
