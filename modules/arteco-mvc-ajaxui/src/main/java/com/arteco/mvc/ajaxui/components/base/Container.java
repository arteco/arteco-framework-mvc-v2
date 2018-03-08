package com.arteco.mvc.ajaxui.components.base;

import java.util.List;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public interface Container {
    <T extends Container, E extends Elem> T append(E... innerElem);
    <T extends Elem> List<T> getInnerElems();
}
