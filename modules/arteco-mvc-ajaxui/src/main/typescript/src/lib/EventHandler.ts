export default class EventHandler {
    constructor(public elem: Node, public  event: string, public  handler: (e: Event) => void, public id: string) {
    }
}
