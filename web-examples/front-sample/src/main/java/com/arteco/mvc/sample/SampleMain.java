package com.arteco.mvc.sample;


import com.arteco.mvc.bootstrap.RunnableWebApp;

/**
 * Created by rarnau on 10/11/16.
 * Arteco Consulting Sl.
 * mailto: info@arteco-consulting.com
 */
public class SampleMain extends RunnableWebApp {

    public static void main(String[] args) {
        RunnableWebApp.run(new SampleApp());
    }
}
