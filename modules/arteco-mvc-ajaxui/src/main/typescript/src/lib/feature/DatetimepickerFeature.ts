import {FeatureHandler} from "../FeatureHandler";
import PageContext from "../PageContext";


export const FEATURE_TYPEAHEAD: string = "typeahead";


export default class implements FeatureHandler {

    enableFeature(ctx: PageContext): void {
        $('*[data-rd-datepicker]').each(function (i, elem) {
            let $elem = $(elem);
            $elem.datetimepicker({
                format: $elem.data("rdDatepickerFormat")
            });
        });
    }

    canHandle(feature: string) {
        return FEATURE_TYPEAHEAD === feature;
    }

}