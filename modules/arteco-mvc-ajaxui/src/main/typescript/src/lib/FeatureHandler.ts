import PageContext from "./PageContext";

export interface FeatureHandler {
    enableFeature(ctx: PageContext): void;

    canHandle(feature: string): boolean;
}