import React from "react"
import "../css/loader.css"

const Loader = () => {
    return (<div className="loader">
        { [...Array(10).keys()].map((number) =><div className="wave"></div>)  }
    </div>)
}

export default Loader