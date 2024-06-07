package com.pipeline.cicd.helpers

class Exception implements Serializable {

    def script

    Exception(script) {
        this.script = script
    }

    void handle(def err) {
        script.currentBuild.result = "FAILURE"
        script.error "Build failed: ${err.toString()}"
    }

}