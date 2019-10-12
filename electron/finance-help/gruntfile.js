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
            electronRun: {
                command: "electron ."
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

};