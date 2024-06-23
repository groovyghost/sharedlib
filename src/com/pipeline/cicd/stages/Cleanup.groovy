package com.pipeline.cicd.stages

import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.Constant

/**
 * Represents a stage in the pipeline that cleans the workspace.
 */
public class Cleanup extends AbstractStage {

    Cleanup(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Cleanup', jenkinsHelper)
    }

    /**
     * Executes the Cleanup stage of the pipeline.
     */
    @Override
    void execute() {
        script.stage(stageName) {
            script.node(Constant.NODE) {
                script.cleanWs(
                        cleanWhenSuccess: true,
                        cleanWhenFailure: false,
                        cleanWhenAborted: true,
                        cleanWhenNotBuilt: true,
                        deleteDirs: true
                )
                // Uncomment the following line to simulate an error for testing notifications
                // script.error 'Simulated error for testing notifications'
            }
        }
    }
}

