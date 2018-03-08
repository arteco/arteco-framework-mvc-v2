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
public class RowElem extends DefaultContainerElem {

    public RowElem(int cols, Elem... elems) {
        super(SlotElem.DIV, new ColElem(cols, elems));
    }

    public RowElem col(int cols, Elem... elem) {
        append(new ColElem(cols, elem));
        return this;
    }

    @Override
    protected void customizeHtmlBuilder(Html.HtmlBuilder builder) {
        builder.styleClass("row");
    }


}
