package com.arteco.mvc.bootstrap.router;

import com.arteco.mvc.bootstrap.WebResponse;

import java.util.function.Supplier;

/**
 * Created by rarnau on 30/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface WebHandler<T, RES extends WebResponse<T>> extends Supplier<RES> {

}
