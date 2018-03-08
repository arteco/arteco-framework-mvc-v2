package com.arteco.mvc.bootstrap.binding.processor;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class NotEmptyValidation extends MinValidation {
    private static NotEmptyValidation instance = new NotEmptyValidation();

    private NotEmptyValidation() {
        super(1, false);
    }

    public static NotEmptyValidation getInstance() {
        return instance;
    }


}
