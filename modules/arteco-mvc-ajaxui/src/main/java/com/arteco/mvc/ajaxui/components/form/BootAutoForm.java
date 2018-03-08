package com.arteco.mvc.ajaxui.components.form;

import com.arteco.mvc.ajaxui.base.PageContext;
import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.base.IOBindingForm;
import com.arteco.mvc.ajaxui.components.grid.SlotElem;
import com.arteco.mvc.ajaxui.components.text.BrElem;
import com.arteco.mvc.ajaxui.components.text.TemplateElem;
import com.arteco.mvc.ajaxui.components.text.TextElem;
import com.arteco.mvc.ajaxui.components.util.Html;
import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebThread;
import com.arteco.mvc.bootstrap.binding.BindingError;
import com.arteco.mvc.bootstrap.binding.BindingResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by rarnau on 15/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
public class BootAutoForm implements Elem {

    private Object bean;
    private String action;
    private BindingResult bindingResult;
    private List<Elem> innerElems = new ArrayList<>();


    public BootAutoForm(String action, IOBindingForm bean, BindingResult bindingResult) {
        this.bean = bean;
        this.action = action;
        this.bindingResult = bindingResult;

        WebContext webContext = WebThread.get();
        PageContext context = webContext.getAttribute(PageContext.class);
        List<FieldDescriptor> finalFields = bean.getFields();

        TreeSet<FieldDescriptor> finalFieldsOrdered = new TreeSet<>(finalFields);
        for (FieldDescriptor fd : finalFieldsOrdered) {
            if (!fd.isSkip()) {
                innerElems.add(fd.getInput());
            }
        }


        String submitCaption = context.getMessage("save", null);
        String formValid = context.getMessage("formValid", null);
        String formInvalid = context.getMessage("formInvalid", null);

        innerElems.add(new TextElem("<button class='btn btn-primary' type='button' data-rd-submit onload='console.log(this)'>" + submitCaption + "</button>"));
        if (bindingResult != null) {
            innerElems.add(new BrElem());
            innerElems.add(new BrElem());
            if (bindingResult.hasErrors()) {
                innerElems.add(new TextElem("<div class='alert alert-danger'>" + formInvalid + "</div>"));
            } else {
                innerElems.add(new TextElem("<div class='alert alert-success'>" + formValid + "</div>"));
            }
            if (bindingResult.hasGlobalErrors()) {
                SlotElem globalErrors;
                innerElems.add(globalErrors = new SlotElem().styleClass("alert alert-danger"));
                for (BindingError errors : bindingResult.getGlobalErrors()) {
                    globalErrors.appendLn(new TextElem(errors.getMessage()));
                }
            }
            innerElems.add(new TemplateElem("classpath:com/arteco/tsajax/controller/page/components/form/BootAutoFormElem.vm"));
        }
    }

    @Override
    public String render() {
        return Html.builder()
                .tag("form")
                .action(action)
                .method("post")
                .innerElems(innerElems)
                .build().render();
    }


}
