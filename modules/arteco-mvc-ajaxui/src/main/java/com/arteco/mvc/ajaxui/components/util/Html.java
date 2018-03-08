package com.arteco.mvc.ajaxui.components.util;

import com.arteco.mvc.ajaxui.components.base.Elem;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by rarnau on 10/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@Builder
public class Html {
    String tag;
    String debug;
    String action;
    String method;
    String styleClass;
    @Singular("data")
    Map<String, String> data;
    @Singular
    List<? extends Elem> innerElems;


    public String render() {
        StringBuilder stb = new StringBuilder();
        stb.append("<").append(tag);
        if (StringUtils.isNotEmpty(styleClass)) {
            stb.append(" class=\"").append(styleClass).append("\"");
        }
        if (StringUtils.isNotEmpty(method)) {
            stb.append(" method=\"").append(method).append("\"");
        }
        if (StringUtils.isNotEmpty(action)) {
            stb.append(" action=\"").append(action).append("\"");
        }
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                if (entry.getValue() != null) {
                    String dataKey = "data-" + entry.getKey();
                    stb.append(" ").append(dataKey).append("=\"").append(entry.getValue()).append("\"");
                }
            }
        }
        stb.append(">");
        if (!CollectionUtils.isEmpty(innerElems)) {
            innerElems.forEach(c -> {
                if (c != null) {
                    stb.append(c.render()).append("\n");
                }
            });
        }
        stb.append("</").append(tag).append(">");
        if (debug != null) {
            stb.append("<!-- ").append(debug).append(" -->");
        }
        return stb.toString();
    }


}
