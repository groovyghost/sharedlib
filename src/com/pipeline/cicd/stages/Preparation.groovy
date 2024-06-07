package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

class Preparation extends AbstractStage {
    Preparation(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Preparation', jenkinsHelper)
    }

    @Override
    void execute() {
        script.stage(stageName) {
            script.node(Constant.NODE) {
                script.checkout script.scm
            }
        }
    }
}

