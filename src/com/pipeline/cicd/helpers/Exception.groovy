package com.pipeline.cicd.helpers

class Exception implements Serializable {

    private final script

    Exception(script) {
        this.script = script
    }

    void handle(Throwable error) {
        script.currentBuild.result = "FAILURE"
        script.error "Build failed: ${error.message}"
    }

}