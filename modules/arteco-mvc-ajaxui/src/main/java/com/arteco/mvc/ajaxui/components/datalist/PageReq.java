package com.arteco.mvc.ajaxui.components.datalist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rarnau on 13/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageReq {
    Long page = 0L;
    Integer pageSize = 10;
}
