package com.pipeline.cicd.helpers

import com.pipeline.cicd.helpers.JenkinsHelper

class Notification implements Serializable {

    private final script

    Notification(script) {
        this.script = script
    }

    private void sendNotification() {
        String emailContent = jenkinsHelper.getEmailContent("Staging")
        script.mail(
            from: 'rahul.a@contus.in',
            to: 'rahula7200@gmail.com',
            cc: 'rahul.a@contus.in',
            subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' + '$BUILD_STATUS',
            body: emailContent
        )
    }
}