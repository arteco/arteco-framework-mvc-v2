import $ from 'jquery';
import "./index.less";
import ActionManager from "./lib/ActionManager";

import 'moment';
import './js/bootstrap-datetimepicker.js';
import './js/bootstrap3-typeahead.js';

let actionManager = new ActionManager();
$(document).ready(function () {
    window["$"] = $;
    let firstReq = true;
    let hash = location.hash.slice(1);
    let targetUrl = "%CONTEXT_PATH%%HOME_PAGE%";
    if (hash != null && hash.length > 0) {
        targetUrl = hash;
    }
    actionManager.makeServerAction({
        targetUrl: targetUrl,
        firstReq: firstReq
    });
});