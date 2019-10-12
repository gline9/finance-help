module.exports = function(grunt) {
    grunt.initConfig({
        shell: {
            backendInstallDist: {
                command: "gradlew installDist"
            },
            backend: {
                command: "gradlew run"
            },
            ngBuild: {
                command: "ng build --base-href ./"
            },
            ngBuildProd: {
                command: "ng build --base-href ./ --prod"
            },
            electronRun: {
                command: "electron ."
            },
            electronBundle: {
                command: "electron-packager . finance-help --no-prune --ignore=/e2e --ignore=/src --ignore=/node_modules --extra-resource=node_modules/minimal-request-promise --extra-resource=node_modules/tree-kill --overwrite --platform win32 --arch x64 --out dist/"
            }
        }
    });

    grunt.loadNpmTasks('grunt-shell');
    grunt.registerTask("build-backend", "Builds gradle backend", function() {
        grunt.file.setBase("..", "..", "backend", "finance-help");
        grunt.task.run("shell:backendInstallDist");
        grunt.file.setBase("..", "..", "electron", "finance-help");
    });

    grunt.registerTask("install-backend-dev", "Installs gradle backend to dev location", function() {
        grunt.file.delete("finance-help");
        grunt.file.copy("../../backend/finance-help/build/install/finance-help", "finance-help")
    });

    grunt.registerTask("update-backend", ["build-backend", "install-backend-dev"]);

    grunt.registerTask("backend", "Runs backend code", function() {
        grunt.file.setBase("..", "..", "backend", "finance-help");
        grunt.task.run("shell:backend");
    });

    grunt.registerTask("electron", ["shell:ngBuild", "shell:electronRun"]);
    
    grunt.registerTask("electron-full", ["update-backend", "electron"]);

    grunt.registerTask("angular-prod", ["shell:ngBuildProd"]);

    grunt.registerTask("copy-electron-dependencies", "Packages electron application", function() {
        grunt.file.copy("dist/finance-help-win32-x64/resources/minimal-request-promise", "dist/finance-help-win32-x64/resources/app/node_modules/minimal-request-promise");
        grunt.file.copy("dist/finance-help-win32-x64/resources/tree-kill", "dist/finance-help-win32-x64/resources/app/node_modules/tree-kill");
    });

    grunt.registerTask("package-electron", ["shell:electronBundle", "copy-electron-dependencies"]);

};