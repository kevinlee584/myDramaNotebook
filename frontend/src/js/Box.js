import React from "react";

import "../css/style.css";
  
const Box = ({ num }) => {
    return (
        <div className="box">
            <h1>Box {num}</h1>
        </div>
    )
};

export default Box;