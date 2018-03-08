package com.arteco.mvc.ajaxui.input;

import com.arteco.mvc.ajaxui.enums.DateType;
import com.arteco.mvc.ajaxui.feature.typeahead.EmptyTypeAheadProvider;
import com.arteco.mvc.ajaxui.feature.typeahead.TypeAheadProvider;
import lombok.Data;

/**
 * Created by rarnau on 5/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class InputDef {
    boolean skip;

    boolean readonly;

    boolean hidden;

    int size = 0;

    int order = Integer.MAX_VALUE;

    String caption = "";

    DateType dateType = DateType.TIMESTAMP;

    Class<? extends TypeAheadProvider> autocompleter = EmptyTypeAheadProvider.class;
}
