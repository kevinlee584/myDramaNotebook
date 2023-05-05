const { ipcRenderer } = require('electron')

window.addEventListener('DOMContentLoaded', () => {
	const actions = [
		["close-button", "close"],
		["min-button", "min"], 
	]

	for(const action of actions) {
		document.getElementById(action[0]).addEventListener("click", _event => {
		ipcRenderer.send('set-controller-events', action[1])})
	}
})

