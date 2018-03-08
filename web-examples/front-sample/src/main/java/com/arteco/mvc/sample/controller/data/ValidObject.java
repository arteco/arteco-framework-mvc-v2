package com.arteco.mvc.sample.controller.data;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidObject {
    @NotNull
    private String nif;
}
