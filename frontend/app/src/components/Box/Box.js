import { useState } from "react";
import RecordService from "../../service/RecordService"
import "./Box.css";
import Save from "./save.svg"
import Saved from "./saved.svg"

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
                <button className="save-button" onClick={saveFun}>
                    { isSaved ? <Saved></Saved> : <Save></Save> }
                </button>
            </>
        </a>
    </div>
    )
};

export default Box;