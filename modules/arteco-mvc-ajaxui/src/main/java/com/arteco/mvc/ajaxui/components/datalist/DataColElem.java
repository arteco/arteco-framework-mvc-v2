package com.arteco.mvc.ajaxui.components.datalist;

import com.arteco.mvc.ajaxui.components.base.Elem;

/**
 * Created by rarnau on 13/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DataColElem<R> implements Elem {
    Elem title;
    LazyElem<R> lazyElem;

    DataColElem(Elem title, LazyElem<R> lazyElem) {
        this.title = title;
        this.lazyElem = lazyElem;
    }

    @Override
    public String render() {
        return title.render();
    }

    public String render(R row) {
        return lazyElem.render(row);
    }

}
