const { app, BrowserWindow } = require("electron");
const path = require("path");
const url = require("url");
 
function handleSquirrelEvent() {
  if (process.argv.length === 1) {
    return false;
  }
 
  const ChildProcess = require('child_process');
 
  const appFolder = path.resolve(process.execPath, '..');
  const rootAtomFolder = path.resolve(appFolder, '..');
  const updateDotExe = path.resolve(path.join(rootAtomFolder, 'Update.exe'));
  const exeName = path.basename(process.execPath);
 
  const spawn = function(command, args) {
    let spawnedProcess, error;
 
    try {
      spawnedProcess = ChildProcess.spawn(command, args, {detached: true});
    } catch (error) {}
 
    return spawnedProcess;
  };
 
  const spawnUpdate = function(args) {
    return spawn(updateDotExe, args);
  };
 
  const squirrelEvent = process.argv[1];
  switch (squirrelEvent) {
    case '--squirrel-install':
    case '--squirrel-updated':
      // Optionally do things such as:
      // - Add your .exe to the PATH
      // - Write to the registry for things like file associations and
      //   explorer context menus
 
      // Install desktop and start menu shortcuts
      spawnUpdate(['--createShortcut', exeName]);
 
      setTimeout(app.quit, 1000);
      return true;
 
    case '--squirrel-uninstall':
      // Undo anything you did in the --squirrel-install and
      // --squirrel-updated handlers
 
      // Remove desktop and start menu shortcuts
      spawnUpdate(['--removeShortcut', exeName]);
 
      setTimeout(app.quit, 1000);
      return true;
 
    case '--squirrel-obsolete':
      // This is called on the outgoing version of your app before
      // we update to the new version - it's the opposite of
      // --squirrel-updated
 
      app.quit();
      return true;
  }
};

// this should be placed at top of main.js to handle setup events quickly
if (handleSquirrelEvent()) {
  // squirrel event handled and app will exit in 1000ms, so don't do anything else
  return;
}
 

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
    win = new BrowserWindow({
        width: 800, height: 600,
        webPreferences: {
            nodeIntegration: true
        }
    });

    //load the dist folder from Angular
    win.loadURL(
        url.format({
            pathname: path.join(__dirname, '/dist-js/index.html'),
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
