package com.arteco.mvc.ajaxui.components.datalist;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by rarnau on 13/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@AllArgsConstructor
public class DataList<T> {
    @Getter
    List<T> list;


}
