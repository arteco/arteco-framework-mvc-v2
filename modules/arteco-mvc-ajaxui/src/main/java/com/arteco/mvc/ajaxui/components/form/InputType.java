package com.arteco.mvc.ajaxui.components.form;

import lombok.Getter;

/**
 * Created by rarnau on 15/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public enum InputType {
    number("number"), date("text"), checkbox("checkbox"), text("text");

    @Getter
    private final String htmlInputType;

    InputType(String htmlInputType) {
        this.htmlInputType = htmlInputType;
    }


}
