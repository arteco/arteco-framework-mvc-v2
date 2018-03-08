package com.arteco.mvc.bootstrap.info;

import lombok.Data;

@Data
public class WebInfo {
    private String name = "Arteco Web App";
    private String author = "Arteco Consulting Sl";
    private String email = "info@arteco-consulting.com";
    private String url = "http://www.arteco-consulting.com";

    private String description;
    private String termsOfService;
    private String version = "1.0.0";

    private String licenseName = "Apache 2.0";
    private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.html";
}
