package com.pipeline.cicd.stages

import com.pipeline.cicd.helpers.JenkinsHelper

/**
 * Abstract class representing a stage in the pipeline.
 */
abstract class AbstractStage implements Stage {

    def stageName
    def script
    JenkinsHelper jenkinsHelper

    AbstractStage(script, String stageName, JenkinsHelper jenkinsHelper) {
        this.script = script
        this.stageName = stageName
        this.jenkinsHelper = jenkinsHelper
    }

    abstract void execute()

}
