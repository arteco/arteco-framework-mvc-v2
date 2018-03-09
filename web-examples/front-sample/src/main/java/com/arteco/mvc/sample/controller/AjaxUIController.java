package com.arteco.mvc.sample.controller;


import com.arteco.mvc.ajaxui.AjaxUIResponse;
import com.arteco.mvc.ajaxui.components.grid.SlotElem;
import com.arteco.mvc.ajaxui.components.navigation.BootAppElem;
import com.arteco.mvc.ajaxui.components.text.H1Elem;
import com.arteco.mvc.ajaxui.components.text.PElem;
import com.arteco.mvc.ajaxui.components.text.TextElem;
import com.arteco.mvc.bootstrap.WebController;
import com.arteco.mvc.bootstrap.router.RouterRegister;

import static com.arteco.mvc.ajaxui.AjaxUIResponse.ajaxUI;

/**
 * Created by rarnau on 10/11/16.
 * Arteco Consulting Sl.
 * mailto: info@arteco-consulting.com
 */
public class AjaxUIController implements WebController {


    private AjaxUIResponse ajaxUiTest() {

        SlotElem slotApp = new SlotElem().targetSlot(BootAppElem.APP);

        slotApp.appendLn(new H1Elem(new TextElem("Hello World!")));
        slotApp.appendLn(new PElem(new TextElem("This text is printed with ajaxui ajax request")));

        return ajaxUI(slotApp);
    }

    @Override
    public void registerPaths(RouterRegister paths) {

        paths.add("/ajaxui/home", this::ajaxUiTest);
    }


}
