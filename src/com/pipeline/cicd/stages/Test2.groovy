package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Test2 extends AbstractStage {

    Test2(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Test2', jenkinsHelper)
    }

    @Override
    void execute() {
            script.stage(stageName) {
                script.node(Constant.NODE) {
                String scriptpath = jenkinsHelper.copyGlobalLibraryScript('test.sh')
                script.sh "bash ${scriptpath} ${script.env.BRANCH_NAME} working"
                script.mail(body: 'Body for mail', cc: 'rahul.a@contus.in', from: 'rahul.a@contus.in', subject: 'Test subject', to: 'rahula7200@gmail.com')
            }
        }
    }
}

