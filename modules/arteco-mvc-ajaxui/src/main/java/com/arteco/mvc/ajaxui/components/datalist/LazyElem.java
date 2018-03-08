package com.arteco.mvc.ajaxui.components.datalist;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.util.ComponentUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;

@Data
@AllArgsConstructor
public class LazyElem<T> implements Elem {

    Function<T, Elem> lazyElem;

    @Override
    public String render() {
        return ComponentUtils.renderNotAllowed();
    }

    public String render(T t) {
        if (lazyElem != null) {
            Elem elem = lazyElem.apply(t);
            if (elem != null) {
                return elem.render();
            }
        }
        return null;
    }
}
