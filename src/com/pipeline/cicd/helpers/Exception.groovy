package com.pipeline.cicd.helpers

/**
 * Exception handling class.
 * Set the build status to FAILED
 * Send email to author
 *
 * @author sathishkumar@contus.in
 * @version 1.0
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
