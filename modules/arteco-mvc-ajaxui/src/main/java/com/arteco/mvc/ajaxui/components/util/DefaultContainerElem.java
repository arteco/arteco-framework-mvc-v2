package com.arteco.mvc.ajaxui.components.util;

import com.arteco.mvc.ajaxui.components.base.ContainerElem;
import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.text.BrElem;

import java.util.List;

/**
 * Created by rarnau on 15/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public abstract class DefaultContainerElem implements ContainerElem {
    protected String tag;
    protected List<Elem> innerElems;
    protected String styleClass;


    public DefaultContainerElem(String tag, Elem... elems) {
        this.tag = tag;
        this.innerElems = ComponentUtils.append(elems);
    }

    public DefaultContainerElem(String tag) {
        this.tag = tag;
    }

    @Override
    public ContainerElem append(Elem... innerElem) {
        this.innerElems = ComponentUtils.append(this.innerElems, innerElem);
        return this;
    }

    @Override
    public List<Elem> getInnerElems() {
        return innerElems;
    }

    public DefaultContainerElem appendLn(Elem... innerElem) {
        innerElems = ComponentUtils.append(innerElems, innerElem);
        innerElems.add(new BrElem());
        return this;
    }

    public DefaultContainerElem styleClass(String styleClass) {
        this.styleClass = styleClass;
        return this;
    }

    @Override
    public String render() {
        Html.HtmlBuilder builder = Html.builder()
                .tag(tag)
                .styleClass(styleClass)
                .innerElems(innerElems);
        customizeHtmlBuilder(builder);
        return builder.build().render();
    }

    protected void customizeHtmlBuilder(Html.HtmlBuilder builder) {
    }

    ;

}
