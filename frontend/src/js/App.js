import React, { useEffect, useState } from "react";

import Box from "./Box";
import Loader from "./loader";
import "../css/app.css"
import actionMap from "./actionMap";
import { serverUrl } from "./AppProperties";


const App = () => {

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [page, setPage] = useState(null)

    // initialize page
    useEffect(() => {

      actionMap.set("setPage", setPage)

      fetch(`${serverUrl}/providers`)
        .then(res => res.json())
        .then((result => {
          const defaultUrl = `${serverUrl}${Object.values(result[0].sorts)[0]}`
          setPage(defaultUrl)
        }), 
        error => {
          setError(error)
        })
    }, [])


    useEffect(() => {
      if (page) {
        setIsLoaded(false)

        fetch(`${page}`)
        .then(res => res.json())
        .then((result) => {
            setItems(result);
            setIsLoaded(true);
        }, 
        (error) => {
          setIsLoaded(true);
          setError(error);
        })
      }
    }, [page])


    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return (<div className="app-frame">
                    <Loader></Loader>
                </div>)
    } else {
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