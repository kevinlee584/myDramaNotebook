// Desktop windows


const { app, screen, BrowserWindow, ipcMain, shell  } = require('electron')
const path = require('path')

const isDev = false

function createWindow () {
	const {width,height} = screen.getPrimaryDisplay().workAreaSize;

	const win = new BrowserWindow({
		width:  isDev ? 800 : 400,
		height: 600,
		resizable: false,
		webPreferences: {
		preload: path.join(__dirname, './preload.js')
		},
		frame: false,
	})

	// win.loadFile('index.html')
	win.loadURL("http://localhost:3000/")


	if (isDev)
		win.webContents.openDevTools();

	win.webContents.setWindowOpenHandler(({ url }) => {
		shell.openExternal(url);
		return { action: 'deny' };
	});

	ipcMain.on('set-controller-events', (_event, ...args) => {
		const controllers = {
			"close": () => win.close(),
			"min": () => win.minimize(),
			"max": () => win.maximize(),
			"restore": () => win.unmaximize(),
		}
		controllers[args[0]]()

	})

}

app.whenReady().then(() => {
createWindow()

app.on('activate', () => {
	if (BrowserWindow.getAllWindows().length === 0) {
		createWindow()
	}
	})
})

app.on('window-all-closed', () => {
	if (process.platform !== 'darwin') {
		app.quit()
	}
})