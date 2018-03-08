import $ from 'jquery';
import EventHandler from "./EventHandler";
import ActionManager from "./ActionManager";
import State from "./State";
import {FeatureHandler} from "./FeatureHandler";
import Datetimepicker from "./feature/DatetimepickerFeature";
import Typeahead from "./feature/TypeaheadFeature";


// TODO: register features
const featureHandlers: Array<FeatureHandler> = [
    new Datetimepicker(),
    new Typeahead()
];


export default class PageContext {
    targetSlots: Map<string, Node> = new Map(); // override targetElem of link;
    scripts: Map<string, string> = new Map();
    features: Map<string, boolean> = new Map();
    eventHandlers: EventHandler[] = [];
    state: State;

    constructor(public actionManager: ActionManager) {
        this.state = actionManager.state;
    }

    addEventHandler(elem: Node, event: string, id: string, handler: (e: Event) => void) {
        this.eventHandlers.push(new EventHandler(elem, event, handler, id));
    }


    addScript(id: string, script: string, parent: Node) {
        if (id && script) {
            (<HTMLElement>parent).id = id;
            this.scripts[id] = script;
        }
    }

    registerEvents() {
        for (let handler of this.eventHandlers) {
            let $t = $("#" + handler.id);
            $t.bind(handler.event, <JQuery.EventHandler<any, HTMLElement>> (<any> handler.handler));
        }
    }

    appendScripts() {
        for (let id in this.scripts) {
            let script = this.scripts[id];
            let scriptNode = document.createElement("script");
            scriptNode.type = "text/javascript";
            scriptNode.appendChild(new Text("/* " + id + " */" + script));
            $("#" + id).append(scriptNode);
        }
    }

    addFeature(feature: string) {
        if (feature) {
            this.features.set(feature, true);
        }
    }

    enableFeatures() {
        this.features.forEach((enabled: boolean, feature: string) => {
            if (enabled) {
                console.log("Enablig Feature :" + feature);
                for (let handler of featureHandlers) {
                    if (handler.canHandle(feature)) {
                        handler.enableFeature(this);
                    }
                }
            }
        });
    }
}