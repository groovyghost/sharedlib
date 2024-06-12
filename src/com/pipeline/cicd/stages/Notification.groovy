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
                String scriptPath = jenkinsHelper.copyGlobalLibraryScript('test.sh')
                script.sh("bash ${scriptPath} ${script.env.BRANCH_NAME} working")
                if(script.currentBuild.result == "SUCCESS"){
                    jenkinsHelper.sendMail('Success', 'rahul.a@contus.in', 'Jenkins', 'Test subject', 'rahula7200@gmail.com')
                }
                else(script.currentBuild.result == "FAILURE"){
                    jenkinsHelper.sendMail('Failed', 'rahul.a@contus.in', 'Jenkins', 'Test subject', 'rahula7200@gmail.com')
                }
                }
            }
        }
    }

