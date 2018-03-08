export default class AbstractDomClass {
    getDataAttr(elem: Node, dataAttribName: string, obligatory: boolean = false): string {
        let rdLink = (<HTMLElement>elem).dataset[dataAttribName];
        if (!rdLink && obligatory) {
            throw elem.nodeName + " needs " + dataAttribName;
        }
        return rdLink;
    }

    setDattaAttr(elem: Node, dataAttribName: string, attribValue: any) {
        (<HTMLElement>elem).dataset[dataAttribName] = attribValue;
    }

}
