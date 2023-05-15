import React, { useEffect, useState } from "react";
import Page from "./Page";
import Loader from "../Loader"
import DramaService from "../../service/DramaService";
import RecordService from "../../service/RecordService";

const style = {
    'marginTop': '44px',
    'marginLeft': '12px',
    'height': 'calc(100% - 56px)',
    'width': '376px',
    'background': 'linear-gradient(45deg, #ffcc80, #ff9900)',
    'boxShadow': 'rgba(6, 24, 44, 0.4) 0px 0px 0px 2px, rgba(6, 24, 44, 0.65) 0px 4px 6px -1px, rgba(255, 255, 255, 0.08) 0px 1px 0px inset',
    'borderRadius': '5vb',
    'overflowY': 'hidden',
    'border': '1px solid #000',
}


export default ({page}) => {
    const [isReload, setIsReload]= useState(false)
    const [isLoad, setIsLoad]= useState(true)
    const [dramas, setDramas] = useState([])
    const [record, setRecord] = useState([])


    useEffect(() => {
        if (isReload) {
            setIsLoad(true)

            DramaService.updateDramas(page)
                .then(res => {
                    setDramas(res)
                    setIsLoad(false)
                    setIsReload(false)
                })
        }
    }, [isReload])

    useEffect(() => {
        setIsLoad(true)
        Promise.all([DramaService.getDramas(page), RecordService.getRecord()])
            .then(values => {
                setDramas(values[0])
                setRecord(values[1])
                setIsLoad(false)
            })
    }, [page])

    if (isLoad) {
        return <div style={style}><Loader></Loader></div>
    }else {
        return <div style={style}><Page dramas={dramas} record={record} reload={() => setIsReload(true)}></Page></div>
    }

}