package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

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
            }
        }
    }
}

