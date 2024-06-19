package com.pipeline.cicd.helpers

import com.pipeline.cicd.helpers.JenkinsHelper

class Notification implements Serializable {
    void sendNotification(String status) {
        String emailContent = jenkinsHelper.getEmailContent("Staging")
        String from = 'rahul.a@contus.in'
        String to = 'rahula7200@gmail.com'
        String cc = 'rahul.a@contus.in'
        String subject = "🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - $status"

        if (from == null || to == null || cc == null || subject == null || emailContent == null) {
            throw new IllegalStateException("One or more required parameters are null")
        }

        script.mail(from: from, to: to, cc: cc, subject: subject, body: emailContent)
    }
}

