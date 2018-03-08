package com.arteco.mvc.ajaxui.components.navigation;

import com.damnhandy.uri.template.UriTemplate;
import lombok.*;

import java.util.Map;

/**
 * Created by rarnau on 13/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {
    String path;
    @Singular
    Map<String, String> params;

    public Link(String path) {
        this.path = path;
    }

    public String build() {
        UriTemplate ucib = UriTemplate.buildFromTemplate(path).build();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                ucib.set(entry.getKey(), entry.getValue());
            }
        }
        return ucib.expand();
    }

    public String toString() {
        return build();
    }
}
