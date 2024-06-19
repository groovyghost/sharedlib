class Notification implements Serializable {

    private def script
    private JenkinsHelper jenkinsHelper

    Notification(script, JenkinsHelper jenkinsHelper) {
        this.script = script
        this.jenkinsHelper = jenkinsHelper
    }

    private void sendNotification(String status) {
        String emailContent = jenkinsHelper.getEmailContent("Staging")
        script.mail(
            from: 'rahul.a@contus.in',
            to: 'rahula7200@gmail.com',
            cc: 'rahul.a@contus.in',
            subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' + status,
            body: emailContent
        )
    }
