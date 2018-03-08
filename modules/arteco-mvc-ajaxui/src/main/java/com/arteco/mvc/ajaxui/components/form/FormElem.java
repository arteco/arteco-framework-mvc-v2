package com.arteco.mvc.ajaxui.components.form;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.util.DefaultContainerElem;
import com.arteco.mvc.ajaxui.components.util.Html;
import lombok.Getter;

/**
 * Created by rarnau on 15/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
public class FormElem extends DefaultContainerElem {

    private String action;

    public FormElem(String action, Elem... elems) {
        super("form", elems);
        this.action = action;
    }


    @Override
    protected void customizeHtmlBuilder(Html.HtmlBuilder builder) {
        builder
                .action(action)
                .method("post");
    }
}
