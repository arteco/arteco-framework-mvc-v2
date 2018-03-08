import ElementHandler from "../ElementHandler";
import PageContext from "../PageContext";
import AbstractElementHandler from "./AbstratElementHandler";


export default class extends AbstractElementHandler implements ElementHandler {
    process(ctx: PageContext, elem: Node, id: string): void {
        let targetSlot = super.getDataAttr(elem, "rdTargetSlot", false);
        if (targetSlot) {
            // console.log("Capturing Slots");
            ctx.targetSlots[targetSlot] = elem;
            elem.parentNode.removeChild(elem);
        }
    }
}
