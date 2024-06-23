package com.pipeline.cicd.stages

import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.Constant

public class Cleanup extends AbstractStage {

    Cleanup(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Cleanup', jenkinsHelper)
    }

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
                // script.error 'Simulated error for testing notifications'
            }
        }
    }
}

