package com.pipeline.cicd.helpers

import com.pipeline.cicd.Constant
import java.lang.NullPointerException

class Notification implements Serializable {

    def script

    Notification(Object script) {
        this.script = script
    }

    String convertHTMLToString(def filename) {
        if (filename == null) {
            throw new NullPointerException("Filename cannot be null")
        }
        def emailContent = script.readFile encoding: 'UTF-8', file: "${filename}"
        return emailContent;
    }

    String getEmailContent() {
        def emailContent = convertHTMLToString("email_template.html")
        if (emailContent == null) {
            throw new NullPointerException("emailContent cannot be null")
        }
        return emailContent
                .replace('${subject}', "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}")
                .replace('${jobName}', script.env.JOB_NAME)
                .replace('${buildNumber}', script.env.BUILD_NUMBER)
                .replace('${content}', "Pipeline ${script.env.JOB_NAME} build ${script.env.BUILD_NUMBER} failed.")
                .replace('${buildUrl}', script.env.BUILD_URL);
    }

    def sendMailNotification() {
        if (Constant.OPS_MAIL == null) {
            throw new NullPointerException("OPS_MAIL cannot be null")
        }
        script.emailext(
            to: Constant.OPS_MAIL,
            subject: "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}",
            body: getEmailContent(),
            recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
        )
    }
}

