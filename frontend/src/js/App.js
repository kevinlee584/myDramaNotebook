import React from "react";

import Box from "./Box";
import "../css/style.css"

const App = () => {

   const n = 10;

   const list = [];
   for(let i=0; i<10; ++i)
      list.push(<Box key={i} num={ i }></Box>)

   return (
      <div className="app">
         {list}
      </div>
   )
};

export default App;