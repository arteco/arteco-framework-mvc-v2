package com.arteco.mvc.bootstrap;

public class RunnableWebApp {
    public static void run(WebAppRegister appRegister) {
        WebApp app = new WebApp();
        appRegister.configure(app);
        app.start();
    }
}
