const { ipcRenderer } = require('electron')

window.addEventListener('DOMContentLoaded', () => {
  const replaceText = (selector, text) => {
    const element = document.getElementById(selector)
    if (element) element.innerText = text
  }

  const actions = [
    ["min-button", "min"], 
    ["max-button", "max"], 
    ["restore-button", "restore"],
    ["close-button", "close"],
  ]

  for(const action of actions) {
    document.getElementById(action[0]).addEventListener("click", _event => {
      ipcRenderer.send('set-controller-events', action[1])
    })
  }
})

