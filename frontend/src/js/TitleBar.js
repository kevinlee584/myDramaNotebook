import React, { useEffect } from "react";

import "../css/titlebar.css"
import "../static/icons/min-w-10.png";
import "../static/icons/max-w-10.png";
import "../static/icons/restore-w-10.png";
import "../static/icons/close-w-10.png";
import "../css/titlebar.css"
import Toggle from "../static/icons/bars-solid.svg"
import actionMap from "./actionMap";


const icons = ["min", "close"]
        .map(m => (
            <div className="button" id={`${m}-button`} key={m}>
                <img className="icon" srcSet={`icons/${m}-w-10.png 1x`} draggable="false" />
            </div>
        ))


const TitleBar = () => {

    useEffect(() => {
        document.getElementById("toggle-button").addEventListener('click', (_event) => {
            const action = actionMap.get("clickBarsBtn")
            if (action) action()
        })
    })

    return (
        <header id="titlebar">
            <div id="drag-region">
                <div id="toggle-button" className="button">
                    <Toggle/>
                </div>
                <div id="window-controls">
                    { icons }
                </div>
            </div>
        </header>
    )
}

export default TitleBar