import React from "react";
import { createRoot } from 'react-dom/client';
import App from "./components/App";

const appRouting = (
	<App></App>
);
const container = document.getElementById('root');
const root = createRoot(container);
root.render(appRouting);