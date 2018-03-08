import ElementHandler from "../ElementHandler";
import PageContext from "../PageContext";
import AbstractElementHandler from "./AbstratElementHandler";


export default class extends AbstractElementHandler implements ElementHandler {
    process(ctx: PageContext, elem: Node, id: string): void {
        let rdSubmit = super.getDataAttr(elem, "rdSubmit");
        if (rdSubmit != null) {
            ctx.addEventHandler(elem, "click", id, function (event: Event) {
                let $form = $(elem).parent("form");
                let rdLink = $form.attr("action");
                console.log("submit " + rdLink);
                ctx.actionManager.makeServerAction({
                    targetUrl: rdLink,
                    method: "post",
                    formElem: $form[0]
                });
            });
        }
    }
}
