import React from "react";
import ReactDOM from "react-dom";

import "./css/style.css"
import App from "./js/App.js";
import TitleBar from "./js/TitleBar.js";
import SidebarMenu from "./js/SideBarMenu";

const appRouting = (
  <>
    <TitleBar></TitleBar>
    <App></App>
    <SidebarMenu></SidebarMenu>
  </>
);

ReactDOM.render(appRouting, document.getElementById("root"));