package com.arteco.mvc.sample.controller;


import com.arteco.mvc.ajaxui.AjaxUIResponse;
import com.arteco.mvc.ajaxui.components.grid.SlotElem;
import com.arteco.mvc.ajaxui.components.navigation.BootAppElem;
import com.arteco.mvc.ajaxui.components.text.H1Elem;
import com.arteco.mvc.ajaxui.components.text.TextElem;
import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebController;
import com.arteco.mvc.bootstrap.response.Responses;
import com.arteco.mvc.bootstrap.router.RouterRegister;

import static com.arteco.mvc.ajaxui.AjaxUIResponse.ajaxUI;

/**
 * Created by rarnau on 10/11/16.
 * Arteco Consulting Sl.
 * mailto: info@arteco-consulting.com
 */
public class AjaxUIController implements WebController {


    private AjaxUIResponse ajaxUiTest() {

        SlotElem slotApp = new SlotElem().targetSlot(BootAppElem.CONTENT);

        slotApp.appendLn(
                new H1Elem(new TextElem("Hello World!")));

        return ajaxUI(slotApp);
    }

    @Override
    public void registerPaths(RouterRegister paths) {
        paths.add("/ajaxui", () -> Responses.redirect("ajaxui/index.html"));
        paths.add("/ajaxui/", () -> Responses.redirect("ajaxui/index.html"));

        paths.add("/ajaxui/app.css", () -> Responses.file("static/ajaxui/app.css", MimeType.CSS));
        paths.add("/ajaxui/app.js", () -> Responses.file("static/ajaxui/app.js", MimeType.JS));
        paths.add("/ajaxui/vendor.js", () -> Responses.file("static/ajaxui/vendor.js", MimeType.JS));
        paths.add("/ajaxui/index.html", () -> Responses.file("static/ajaxui/index.html", MimeType.HTML));
        paths.add("/ajaxui/test", this::ajaxUiTest);
    }


}
