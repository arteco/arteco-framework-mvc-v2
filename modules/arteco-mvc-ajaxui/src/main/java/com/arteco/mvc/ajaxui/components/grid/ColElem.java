package com.arteco.mvc.ajaxui.components.grid;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.util.DefaultContainerElem;
import com.arteco.mvc.ajaxui.components.util.Html;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ColElem extends DefaultContainerElem {
    private int cols;

    public ColElem(int cols, Elem... elem) {
        super(SlotElem.DIV, elem);
        this.cols = cols;
    }


    @Override
    protected void customizeHtmlBuilder(Html.HtmlBuilder builder) {
        builder.styleClass("col-md-" + cols)
                .debug("col-" + cols + " end");
    }
}
