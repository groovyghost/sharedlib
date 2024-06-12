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
                String scriptPath = jenkinsHelper.copyGlobalLibraryScript('test.sh')
                script.sh("bash ${scriptPath} ${script.env.BRANCH_NAME} working")
                script.post {
                    failure {
                        sendMail('Failed', 'rahul.a@contus.in', 'Jenkins', 'Test subject', 'rahula7200@gmail.com')
                    }

                    success {
                        sendMail('Success', 'rahul.a@contus.in', 'Jenkins', 'Test subject', 'rahula7200@gmail.com')
                    }
                }
            }
        }
    }

    private void sendMail(String status, String from, String to, String cc, String subject, String body) {
        script.mail(
            from: from,
            to: to,
            cc: cc,
            subject: "${status} - ${subject}",
            body: body
        )
    }
}
