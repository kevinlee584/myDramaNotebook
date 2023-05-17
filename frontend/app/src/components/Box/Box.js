import React, { useState } from "react";
import RecordService from "../../service/RecordService"
import Save from "./save.svg"
import Saved from "./saved.svg"
import "./Box.css";

const Box = ({ drama, record }) => {

    const { providerName, imageUrl, videoUrl, name } = drama
    const [isSaved, setIsSaved] = useState(record.find(e => e.providerName == providerName && e.name == name) != undefined)

    const saveFun = (event) => {
        event.preventDefault()
        if (isSaved) {
            RecordService.removeDrama(providerName, name)
            .then(_res => {
                setIsSaved(false)
                let index = record.findIndex(e => e.providerName == providerName && e.name == name)
                record.splice(index, 1)
                })
        }else {
            RecordService.addDrama(providerName, name)
            .then(_res => {
                setIsSaved(true)
                record.push(drama)
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
                <div className="save-botton" onClick={saveFun}>
                    { isSaved ? <Saved></Saved> : <Save></Save> }
                </div>
            </>
        </a>
    </div>
)










    // const { providerName, imageUrl, videoUrl, name } = drama
    // const isSave = record.find(e => e.providerName == providerName && e.name == name) != undefined
    // const [saved, setSaved] = useState(isSave)

    // const clicked = (event) => {
    //     event.preventDefault()
    //     if (saved) {
    //         fetch(`${config.backend.url}/user/remove`, 
    //             {method: "DELETE", headers: headers, 
    //             body: JSON.stringify({provider: providerName, name: name})})
    //         .then(res => {
    //             setSaved(false)
    //             let index = record.findIndex(e => e.providerName == providerName && e.name == name)
    //             record.splice(index, 1)
    //             setRecord(record)
    //         })
    //     }
    //     else {
    //         fetch(`${config.backend.url}/user/save`, 
    //             {method: "POST", 
    //             headers: headers, 
    //             body: JSON.stringify({provider: providerName, name: name})})
    //         .then(res => {
    //             setSaved(true)
    //             record.push(drama)
    //             setRecord(record)
    //         })
    //     }
    // }

    // return (
    //     <div className="box">
    //         <a href={videoUrl} className="video-block" target="_blank">
    //             <>
    //                 <img src={imageUrl} className="video-image"></img>
    //                 <div className="video-name-blocker">
    //                     <div className="video-name">
    //                         <p>{name}</p>
    //                         <p>{name}</p>
    //                     </div>
    //                 </div>
    //                 <div className="save-botton" onClick={clicked}>
    //                     { saved ? <Saved></Saved> : <Save></Save> }
    //                 </div>
    //             </>
    //         </a>
    //     </div>
    // )
};

export default Box;