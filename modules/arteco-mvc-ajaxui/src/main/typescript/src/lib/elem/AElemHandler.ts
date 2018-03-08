import ElementHandler from "../ElementHandler";
import PageContext from "../PageContext";
import AbstractElementHandler from "./AbstratElementHandler";

export default class extends AbstractElementHandler implements ElementHandler {
    process(ctx: PageContext, elem: Node, id: string): void {
        let rdLink = super.getDataAttr(elem, "rdLink");
        if (rdLink) {
            (<HTMLAnchorElement> elem).href = "#" + rdLink;
            ctx.addEventHandler(elem, "click", id,
                function (event: Event) {
                    window.location.hash = rdLink;
                    ctx.actionManager.makeServerAction({
                        targetUrl: rdLink
                    });
                });
        }
    }
}
