import ElementHandler from "../ElementHandler";
import PageContext from "../PageContext";
import AbstractElementHandler from "./AbstratElementHandler";

/**
 * El elemento <script> debe tener obligatoriamente [type="javascript"]
 * Si no, no será procesado por este ElementHandler
 */
export default class extends AbstractElementHandler implements ElementHandler {
    process(ctx: PageContext, elem: Node, id: string): void {
        let scriptLines = elem.childNodes;
        if (scriptLines != null && scriptLines.length > 0) {
            // console.log("Scripting ");
            scriptLines.forEach((child: Node) => {
                ctx.addScript(id, child.textContent, elem.parentNode);
            });
        }
        elem.parentNode.removeChild(elem);
    }
}
