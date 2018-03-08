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
public class SlotElem extends DefaultContainerElem {

    private String slotName;
    private String targetSlot;

    public static String DIV = "div";

    public SlotElem(String slotName) {
        super(DIV);
        this.slotName = slotName;
    }

    public SlotElem() {
        super(DIV);
    }

    @Override
    public SlotElem append(Elem... innerElem) {
        return (SlotElem) super.append(innerElem);
    }

    @Override
    public SlotElem appendLn(Elem... innerElem) {
        return (SlotElem) super.appendLn(innerElem);
    }

    @Override
    public SlotElem styleClass(String styleClass) {
        return (SlotElem) super.styleClass(styleClass);
    }

    @Override
    protected void customizeHtmlBuilder(Html.HtmlBuilder builder) {
        builder.data("rd-slot", slotName)
                .data("rd-target-slot", targetSlot);
    }


    public SlotElem targetSlot(String targetSlot) {
        this.targetSlot = targetSlot;
        return this;
    }
}
