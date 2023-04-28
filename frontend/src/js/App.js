import React, { useEffect, useState } from "react";

import Box from "./Box";
import "../css/app.css"

const App = () => {

   const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/provider/bahamut/hot")
        .then(res => res.json())
        .then(
          (result) => {
            setIsLoaded(true);
            setItems(result);
          },
          // Note: it's important to handle errors here
          // instead of a catch() block so that we don't swallow
          // exceptions from actual bugs in components.
          (error) => {
            setIsLoaded(true);
            setError(error);
          }
        )
    }, [])

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {

      console.log(items)

      return (
         <div className="app-frame">
            <div className="app">
          {items.map(item => (
              <Box key={item.name} imageUrl={item.imageUrl} videoUrl={item.videoUrl} videoName={item.name}></Box>
          ))}
          </div>
         </div>
         );
    }

};

export default App;