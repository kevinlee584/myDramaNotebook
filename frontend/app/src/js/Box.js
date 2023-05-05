import React, { useEffect } from "react";

import "../css/box.css";
  
const Box = ({ imageUrl, videoUrl, videoName }) => {

    return (
        <div className="box">
            <a href={videoUrl} className="video-block" target="_blank">
                <>
                    <img src={imageUrl} className="video-image"></img>
                    <div className="video-name-blocker">
                        <div className="video-name">
                            <p>{videoName}</p>
                            <p>{videoName}</p>
                        </div>
                    </div>
                </>
            </a>
        </div>
    )
};

export default Box;