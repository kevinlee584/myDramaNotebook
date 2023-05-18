import { useEffect } from "react";

import "./Title.css"
import "./min-w-10.png";
import "./close-w-10.png";
import Toggle from "./bars-solid.svg"

const icons = ["min", "close"]
        .map(m => (
            <div className="button" id={`${m}-button`} key={m}>
                <img className="icon" srcSet={`icons/${m}-w-10.png 1x`} draggable="false" />
            </div>
        ))


const Title = ({showMenu, setShowMenu}) => {

    useEffect(() => {
        window.dispatchEvent( new Event("title-bar-ready") )
    }, [])

    return (
        <header id="title">
            <div id="drag-region">
                <div id="toggle-button" onClick={() => setShowMenu(!showMenu)} className="button">
                    <Toggle/>
                </div>
                <div id="window-controls">
                    { icons }
                </div>
            </div>
        </header>

    )
}

export default Title