import React, {useContext, useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import {features} from "fixers-did-domain";
import {DidHttpClientContext, DidRSocketClientContext} from "./did/context/DidClientContext";

function App() {
    const httpDidClient = useContext(DidHttpClientContext);
    const rsocketDidClient = useContext(DidRSocketClientContext);

    const [rsocketEvent, setRsocketEvent] = useState<features.DidCreatedEvent | undefined>()
    const [httpEvent, setHttpEvent] = useState<features.DidCreatedEvent | undefined>()
    useEffect(() => {
        const cmd = new features.DidCreateCommandPayload("httpDid")
        !!httpDidClient && httpDidClient.client && httpDidClient.client.createDid(cmd).then((event: features.DidCreatedEvent) => {
            console.log("///////////////////////////")
            console.log("http: " + event.id)
            console.log("///////////////////////////")
            setHttpEvent(event)
        })
    }, [httpDidClient.initialized])

    useEffect(() => {
        const cmd = new features.DidCreateCommandPayload("rsocketDid")
        !!rsocketDidClient && rsocketDidClient.client && rsocketDidClient.client.createDid(cmd).then((event: features.DidCreatedEvent) => {
            console.log("=========================")
            console.log("rsocket: " + event.id)
            console.log("=========================")
            setRsocketEvent(event)
        })
    }, [rsocketDidClient.initialized])

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                {rsocketEvent?.id}
                <br/>
                {httpEvent?.id}
            </header>
        </div>
    );
}

export default App;
