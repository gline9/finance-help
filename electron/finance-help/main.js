const { app, BrowserWindow } = require("electron");
const path = require("path");
const url = require("url");

platform = process.platform;

if (platform === 'win32') {
    serverProcess = require("child_process")
        .spawn('cmd.exe', ['/c', 'finance-help.bat'],
            {
                cwd: app.getAppPath() + '/finance-help/bin'
            });
} else {
    serverProcess = require('child_process')
        .spawn(app.getAppPath() + '/finance-help/bin/finance-help');
}

let win;

let serverUrl = 'http://localhost:5150';

function createWindow()
{
    win = new BrowserWindow({ width: 800, height: 600 });

    //load the dist folder from Angular
    win.loadURL(
        url.format({
            pathname: path.join(__dirname, '/dist/index.html'),
            protocol: "file:",
            slashes: true
        })
    );

    // The following is optional and will open the DevTools:
    // win.webContents.openDevTools()

    win.on("closed", () => {
        win = null;
    });

    win.on("close", (e) => {
        if (serverProcess) {
            e.preventDefault();
            const kill = require('tree-kill');
            kill(serverProcess.pid, 'SIGTERM', () => {
                console.log('Server process killed');

                serverProcess = null;

                win.close();
            });
        }
    });

}

// app.on("ready", createWindow);

// on macOS, closing the window doesn't quit the app
app.on("window-all-closed", () => {
    if (process.platform !== "darwin") {
        app.quit();
    }
});

function startUp() {
    const requestPromise = require('minimal-request-promise');

    requestPromise.get(serverUrl).then(() => {
        console.log("Server started!");
        createWindow();
    }, () => {
        console.log("Waiting for the server start...");
        setTimeout(() => {
            startUp();
        }, 200);
    });
}

// initialize the app's main window
// app.on("activate", () => {
//     if (win === null) {
//         createWindow();
//     }
// });

startUp();
