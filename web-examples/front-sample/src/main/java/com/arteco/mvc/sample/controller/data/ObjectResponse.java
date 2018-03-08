package com.arteco.mvc.sample.controller.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectResponse {

    String message;
    Instant time;

}
