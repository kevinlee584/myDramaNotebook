import React from "react";
import ReactDOM from "react-dom";

import "./css/style.css"
import App from "./js/App.js";
import TitleBar from "./js/TitleBar.js";
import MenuBar from "./js/MenuBar"; 

const appRouting = (
	<>
		<TitleBar></TitleBar>
		<App></App>
		<MenuBar></MenuBar>
	</>
);

ReactDOM.render(appRouting, document.getElementById("root"));