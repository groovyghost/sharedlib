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

                if(script.currentBuild.result == "SUCCESS"){
                    script.mail(
                        from: 'rahul.a@contus.in',
                        to: 'rahula7200@gmail.com',
                        cc: 'rahul.a@contus.in',
                        subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' +'$BUILD_STATUS',
                        body: jenkinsHelper.getEmailContent("Staging")
                        )
                }

                else(script.currentBuild.result == "FAILURE"){
                    script.mail(
                        from: 'rahul.a@contus.in',
                        to: 'rahula7200@gmail.com',
                        cc: 'rahul.a@contus.in',
                        subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' +'$BUILD_STATUS',
                        body: jenkinsHelper.getEmailContent("Staging")
                        )
                }
            }
        }
    }

