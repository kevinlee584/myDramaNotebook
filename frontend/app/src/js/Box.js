import React, { useEffect, useState } from "react";
import Save from "../static/icons/save.svg"
import Saved from "../static/icons/saved.svg"
import config from '../../../config'
import "../css/box.css";

let headers = {
    "Content-Type": "application/json",
    "Accept": "application/json, text/plain;charset=UTF-8"
}

const Box = ({ drama, record, setRecord }) => {

    const { providerName, imageUrl, videoUrl, name } = drama
    const isSave = record.find(e => e.providerName == providerName && e.name == name) != undefined
    const [saved, setSaved] = useState(isSave)

    const clicked = (event) => {
        event.preventDefault()
        if (saved) {
            fetch(`${config.backend.url}/user/remove`, 
                {method: "DELETE", headers: headers, 
                body: JSON.stringify({provider: providerName, name: name})})
            .then(res => {
                setSaved(false)
                let index = record.findIndex(e => e.providerName == providerName && e.name == name)
                record.splice(index, 1)
                setRecord(record)
            })
        }
        else {
            fetch(`${config.backend.url}/user/save`, 
                {method: "POST", 
                headers: headers, 
                body: JSON.stringify({provider: providerName, name: name})})
            .then(res => {
                setSaved(true)
                record.push(drama)
                setRecord(record)
            })
        }
    }

    return (
        <div className="box">
            <a href={videoUrl} className="video-block" target="_blank">
                <>
                    <img src={imageUrl} className="video-image"></img>
                    <div className="video-name-blocker">
                        <div className="video-name">
                            <p>{name}</p>
                            <p>{name}</p>
                        </div>
                    </div>
                    <div className="save-botton" onClick={clicked}>
                        { saved ? <Saved></Saved> : <Save></Save> }
                    </div>
                </>
            </a>
        </div>
    )
};

export default Box;