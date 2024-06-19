package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Notification extends AbstractStage {

    Notification(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Notification', jenkinsHelper)
    }

    @Override
    void execute() {
        script.stage(stageName) {
            script.node(Constant.NODE) {

                String buildStatus = script.currentBuild.result
                if(buildStatus != null) {
                    String emailContent = jenkinsHelper.getEmailContent("Staging")
                    if(buildStatus == "SUCCESS") {
                        script.mail(
                            from: 'rahul.a@contus.in',
                            to: 'rahula7200@gmail.com',
                            cc: 'rahul.a@contus.in',
                            subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' + buildStatus,
                            body: emailContent
                        )
                    } else {
                        script.mail(
                            from: 'rahul.a@contus.in',
                            to: 'rahula7200@gmail.com',
                            cc: 'rahul.a@contus.in',
                            subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' + buildStatus,
                            body: emailContent
                        )
                    }
                }
            }
        }
    }
}