import ElementHandler from "../ElementHandler";
import PageContext from "../PageContext";
import AbstractElementHandler from "./AbstratElementHandler";
import {FEATURE_DATEPICKER} from "../feature/TypeaheadFeature";

export default class extends AbstractElementHandler implements ElementHandler {
    process(ctx: PageContext, elem: Node, id: string): void {
        let rdDatePicker = super.getDataAttr(elem, "rdDatepicker");
        if (rdDatePicker) {
            ctx.addFeature(FEATURE_DATEPICKER);
        }
    }
}
