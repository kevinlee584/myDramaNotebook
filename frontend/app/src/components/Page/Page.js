import { useEffect, useState } from "react";
import Reload from "./reload.svg"
import Box from "../Box"
import "./Page.css"

function Page({dramas, record, reload}){

    const [showed, setShowed] = useState(10)

    useEffect(() => {
		document.getElementById("page").scrollTop = 0
    }, [dramas])

    var moreButton = <div style={{height: '20px'}}></div>
    if (dramas.length > showed) {
        moreButton = <button className="more-button" onClick={() => {setShowed(showed + 10 )}}>more</button> 
    }

	const len = showed > dramas.length ? dramas.length : showed
    const drama_blocker = []
    for (let i=0; i<len ; ++i){ 
        drama_blocker.push(
            <Box key={dramas[i].name} drama={dramas[i]} record={record}></Box>
        )
    }
	
    return (
        <>
            <button id="reload-button" onClick={ () => {reload(); setShowed(10);} }><Reload></Reload></button>
            <div id="page">
                { drama_blocker }
                { moreButton }
            </div>
        </>
    )
}

export default Page;