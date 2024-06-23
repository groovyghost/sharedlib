package com.pipeline.cicd.helpers

/**
 * Represents an exception handler for the pipeline.
 *
 * This class is responsible for handling exceptions that occur during the pipeline execution.
 * It sets the build result to "FAILURE" and sends an error message with the exception details.
 */
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