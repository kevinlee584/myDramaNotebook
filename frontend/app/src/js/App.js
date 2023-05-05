import React, { useEffect, useState } from "react";

import Box from "./Box";
import Loader from "./Loader";
import actionMap from "./actionMap";
import Reload from '../static/icons/reload.svg'
import "../css/app.css"
import config from '../../../config'

const serverUrl = config.backend.url
const dramaMap = new Map()

const App = () => {

	const [error, setError] = useState(null);
	const [isLoaded, setIsLoaded] = useState(false);
	const [items, setItems] = useState([[], null]);
	const [page, setPage] = useState(null)

	// initialize page
	useEffect(() => {

		actionMap.set("setPage", setPage)

		fetch(`${serverUrl}/providers`)
		.then(res => res.json())
		.then((result => {
			const defaultUrl = `${serverUrl}${Object.values(result[0].sorts)[0]}`
			actionMap.get("setPage")(defaultUrl)
		}), 
		error => {
			setError(error)
		})
	}, [])


	useEffect(() => {
		if (!page) return

		var p = page

		if (p.startsWith("update: ")) {
			p = /http:\/\/.*/.exec(p)[0]
			dramaMap.delete(p)
			setIsLoaded(false)
		}else {
			const r = dramaMap.get(page)
			if (r != undefined) {
				setItems(r);
				return;
			}
		}

		setIsLoaded(false)

		fetch(p)
		.then(res => res.json())
		.then((result) => {
			let r = [result, result.size < 10 ? result.size : 10]
			dramaMap.set(page, r)
			setItems(r);
			setIsLoaded(true);
		}, 
		(error) => {
			setIsLoaded(true);
			setError(error);
		})
		
		
	}, [page])

	if (error) {
		return <div>Error: {error.message}</div>;
	} else if (!isLoaded) {
		return (
			<div className="app-frame">
				<Loader></Loader>
			</div>)
	} else {
		var boxs = [];
		for(let i=0; i<Math.min(items[0].length, items[1]); ++i)
			boxs.push(<Box key={items[0][i].name} imageUrl={items[0][i].imageUrl} videoUrl={items[0][i].videoUrl} videoName={items[0][i].name}></Box>)

		var reload = () => {
			setPage(`update: ${page}`)
		}

		return (
			<div className="app-frame">
				<button className="reload-button" onClick={ reload }><Reload></Reload></button>
				<div className="app">
					{ boxs }
					{ items[0].length > items[1] ? 
						<button className="more-button" onClick={() => {dramaMap.set(page, [items[0], items[1] + 10]); setItems([items[0], items[1] + 10]);}}>more</button> 
						: <div style={{height: '20px'}}></div>}
				</div>
			</div>
		);
	}

};

export default App;