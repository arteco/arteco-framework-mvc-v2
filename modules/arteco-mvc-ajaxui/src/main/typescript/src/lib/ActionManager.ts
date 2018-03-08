import $ from 'jquery';

import PageContext from "./PageContext";
import ElementHandler from "./ElementHandler";
import AElemHandler from "./elem/AElemHandler";
import DivElemHandler from "./elem/DivElemHandler";
import State from "./State";
import AbstractDomClass from "./AbstractDomClass";
import ScriptElemHandler from "./elem/ScriptElemHandler";
import ButtonElemHandler from "./elem/ButtonElemHandler";
import InputElemHandler from "./elem/InputElemHandler";

export interface ActionDesc {
    targetUrl: string;
    method?: string;
    formElem?: Node;
    firstReq?: boolean;
}

export default class ActionManager extends AbstractDomClass {

    uid = 0;
    state = new State();

    makeServerAction(action: ActionDesc) {
        let $this = this;
        let ctx = new PageContext(this);

        let method = action.method;
        let targetUrl = action.targetUrl;
        let formElem = action.formElem;

        function handler(data: string) {
            $this.parseResponse(data, targetUrl, ctx);
            // update every slot
            $this.mergeSlots(ctx);
            // register handlers
            ctx.registerEvents();
            // add scripts
            ctx.appendScripts();
            // enable features
            ctx.enableFeatures();
            // trigger events;
            $this.triggerEvents(ctx);
        }


        function getFormData($form) {
            let unindexed_array = $form.serializeArray();
            let indexed_array = {};

            $.map(unindexed_array, function (n, i) {
                indexed_array[n['name']] = n['value'];
            });

            return indexed_array;
        }

        function errorHandler() {
            alert("Error returned from Server !!!!!!");
        }


        if (!method) {
            method = "get";
        }

        switch (method) {
            case "get":
                if (action.firstReq) {
                    // si es la primera llamada de la app, se deben iniciar los slots
                    if (targetUrl.indexOf("?") > -1) {
                        targetUrl += "&_fq=true";
                    } else {
                        targetUrl += "?_fq=true";
                    }
                }
                $.get(targetUrl, handler)
                    .fail(errorHandler);
                break;
            case "post":
                let $form = $(formElem);
                let formData = getFormData($form);
                $.post(targetUrl, formData, handler)
                    .fail(errorHandler);
                break;
            default:
                throw "Method not allowed";
        }

    }

    parseResponse(data: string, targetUrl: string, ctx: PageContext) {
        let nodes: JQuery.Node[] = $.parseHTML(data.replace(/<\/?response>/gi, ''));

        if (nodes == null || nodes.length === 0) {
            console.warn("No response fond for " + targetUrl);
        }

        // parse an update virtual dom
        for (let node of nodes) {
            this.visit(ctx, node);
        }
    }

    mergeSlots(ctx: PageContext) {
        console.log("Merging Slots");
        for (let slot in ctx.targetSlots) {
            let targetElem = this.localeSlotElem(slot);
            if (!targetElem) {
                throw "Target Element Not Found for Slot [" + slot + "]";
            }
            $(targetElem).html("").append(ctx.targetSlots[slot]);
        }
    }

    visit(ctx: PageContext, elem: Node): void {
        this.processElem(ctx, elem);
        if (elem.hasChildNodes()) {
            for (let i = 0; i < elem.childNodes.length; i++) {
                this.visit(ctx, elem.childNodes[i]);
            }
        }
    }

    localeSlotElem(slot2Find: string) {
        return $('*[data-rd-slot="' + slot2Find + '"]')[0];
    }


    processElem(ctx: PageContext, elem: Node): void {
        let handler: ElementHandler = this.getElementHandler(elem.nodeName);
        if (handler) {
            let id: string = this.getOrSetId(elem);
            handler.process(ctx, elem, id);
        }
    }


    getElementHandler(nodeName: string): ElementHandler {
        switch (nodeName) {
            case 'A':
                return new AElemHandler();
            case 'DIV':
                return new DivElemHandler();
            case 'BUTTON':
                return new ButtonElemHandler();
            case 'INPUT':
                return new InputElemHandler();
            case 'SCRIPT':
                return new ScriptElemHandler();

        }
        return null;
    }

    getOrSetId(elem: Node): string {
        let e = (<HTMLElement>elem);
        let id = e.id;
        if (!id) {
            id = this.newUuid();
            e.id = id;
        }
        return id;
    }


    newUuid(): string {
        // return <string> String(this.uid++);
        let ALPHABET = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

        let ID_LENGTH = 16;

        let rtn = '';
        for (let i = 0; i < ID_LENGTH; i++) {
            rtn += ALPHABET.charAt(Math.floor(Math.random() * ALPHABET.length));
        }
        return rtn;
    }

    triggerEvents(ctx: PageContext): void {
        // data-rd-event-link
        let $this = this;
        let $links = $('*[data-rd-event-link]');
        $links.each(function (i, elem) {
            let linkEvent = $this.getDataAttr(elem, "rdEventLink");
            if (linkEvent) {
                try {
                    window[linkEvent](elem, ctx);
                } catch (e) {
                    console.error(e);
                }
            }
        });
    }

}

