import React from "react";

import "../css/titlebar.css"
import "../static/icons/min-w-10.png";
import "../static/icons/max-w-10.png";
import "../static/icons/restore-w-10.png";
import "../static/icons/close-w-10.png";
import "../css/titlebar.css"

const TitleBar = () => {

    var icons = ["min", "max", "restore", "close"]
        .map(m => (
            <div className="button" id={`${m}-button`} key={m}>
                <img className="icon" srcSet={`icons/${m}-w-10.png 1x`} draggable="false" />
            </div>
        ))



    return (
        <header id="titlebar">
            <div id="drag-region">
                <div id="window-title">
                    <span>Electron quick start</span>
                </div>
                <div id="window-controls">
                    { icons }
                </div>
            </div>
        </header>
    )
}

export default TitleBar