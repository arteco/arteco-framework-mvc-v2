import PageContext from "./PageContext";

export default interface ElementHandler {
    process(ctx: PageContext, elem: Node, id: string): void;
}