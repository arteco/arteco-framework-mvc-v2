package com.arteco.mvc.bootstrap.response.txt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rarnau on 9/3/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Replacement {
    String source;
    String target;
}
