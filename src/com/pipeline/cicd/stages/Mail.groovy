package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Mail extends AbstractStage {

    Mail(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Mail', jenkinsHelper)
    }

    @Override
    void execute() {
            script.stage(stageName) {
                script.node(Constant.NODE) {
                String scriptpath = jenkinsHelper.copyGlobalLibraryScript('test.sh')
                script.sh "bash ${scriptpath} ${script.env.BRANCH_NAME} working"
                //script.mail(body: 'Body for mail', cc: 'rahul.a@contus.in', from: 'Jenkins', subject: 'Test subject', to: 'rahula7200@gmail.com')
                script.emailext(body: 'Body for mail', from: 'Jenkins', subject: 'Test subject', to: 'rahula7200@gmail.com,rahul.a@contus.in')
            }
        }
    }
}

