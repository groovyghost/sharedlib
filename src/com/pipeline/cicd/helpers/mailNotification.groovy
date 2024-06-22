package com.pipeline.cicd.helpers

import com.pipeline.cicd.Constant

class MailNotification implements Serializable {

    def script

    MailNotification(Object script) {
        this.script = script
    }

    void sendMailNotification(Exception error) {
        def templateContent = JenkinsHelper.getEmailTemplate()
        def emailContent = templateContent
                .replace('${subject}', "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}")
                .replace('${jobName}', script.env.JOB_NAME)
                .replace('${buildNumber}', script.env.BUILD_NUMBER)
                .replace('${content}', "Pipeline ${script.env.JOB_NAME} build ${script.env.BUILD_NUMBER} failed.")
                .replace('${buildUrl}', script.env.BUILD_URL)

        script.emailext(
            to: Constant.OPS_MAIL,
            subject: "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}",
            body: """<p>Pipeline <b>${script.env.JOB_NAME}</b> build <b>${script.env.BUILD_NUMBER}</b> failed.</p>
                    <p>Error: ${error.message}</p>
                    <p>Check the details at: ${script.env.BUILD_URL}</p>""",
            recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
        )
    }
}

