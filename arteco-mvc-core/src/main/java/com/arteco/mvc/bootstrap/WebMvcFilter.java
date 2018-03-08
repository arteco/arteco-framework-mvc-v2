package com.arteco.mvc.bootstrap;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;

/**
 * Created by rarnau on 26/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Slf4j
public class WebMvcFilter extends WebMvc implements Filter {

    public static final String NAME = "artecoMainFilter";

    public WebMvcFilter(WebApp application) {
        super(application);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("Init WebMvcFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        super.serve(request, response);
    }

}
