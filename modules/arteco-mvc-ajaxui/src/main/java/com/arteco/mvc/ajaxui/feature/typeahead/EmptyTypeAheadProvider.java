package com.arteco.mvc.ajaxui.feature.typeahead;

import com.arteco.mvc.ajaxui.components.datalist.PagedList;
import com.arteco.mvc.ajaxui.components.form.LabelValue;

/**
 * Created by rarnau on 24/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class EmptyTypeAheadProvider implements TypeAheadProvider {
    @Override
    public PagedList<LabelValue> getOptions(String term) {
        return null;
    }
}
