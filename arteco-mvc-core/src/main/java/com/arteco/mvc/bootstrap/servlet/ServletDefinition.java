package com.arteco.mvc.bootstrap.servlet;

import javax.servlet.Servlet;

public interface ServletDefinition extends Definition {

    Servlet getServlet();
}
