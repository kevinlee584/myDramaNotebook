import React from "react";
import ReactDOM from "react-dom";

import App from "./js/App.js";
import TitleBar from "./js/TitleBar.js";

const appRouting = (
  <div>
    <TitleBar></TitleBar>
    <App></App>
  </div>
);

ReactDOM.render(appRouting, document.getElementById("root"));