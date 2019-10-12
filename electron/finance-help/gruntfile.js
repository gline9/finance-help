module.exports = function(grunt) {
    grunt.initConfig({
        shell: {
            backendInstallDist: {
                command: "gradlew installDist"
            }
        }
    });

    grunt.loadNpmTasks('grunt-shell');
    grunt.registerTask("build-backend", "Builds gradle backend", function() {
        grunt.file.setBase("..", "..", "backend", "finance-help");
        grunt.task.run("shell:backendInstallDist");
    });

    grunt.registerTask("install-backend-dev", "Installs gradle backend to dev location", function() {
        grunt.file.delete("finance-help");
    });

};