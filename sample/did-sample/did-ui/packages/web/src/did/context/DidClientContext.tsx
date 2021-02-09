import React from "react";
import {DidAggregateJsClient} from "fixers-did-domain";

export type DidClientContextType = {
    client?: DidAggregateJsClient;
    initialized: Boolean;
};

export const DidHttpClientContext = React.createContext<DidClientContextType>({initialized: false});
export const DidRSocketClientContext = React.createContext<DidClientContextType>({initialized: false});
