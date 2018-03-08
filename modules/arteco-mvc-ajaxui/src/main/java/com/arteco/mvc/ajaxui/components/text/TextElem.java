package com.arteco.mvc.ajaxui.components.text;

import com.arteco.mvc.ajaxui.components.base.Elem;
import lombok.Data;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class TextElem implements Elem {
    private String text;

    public TextElem(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return text;
    }
}
