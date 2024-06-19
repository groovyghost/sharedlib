package com.pipeline.cicd.helpers

import java.io.File

class JenkinsHelper implements Serializable {

    private final script

    JenkinsHelper(script) {
        this.script = script
    }

    String createTemporaryPath(String path) {
        String temporaryDirectory = script.pwd(tmp: true)
        return temporaryDirectory + File.separator + new File(path).getName()
    }

    String copyGlobalLibraryScript(String sourcePath, String destinationPath = null) {
        destinationPath = destinationPath ?: createTemporaryPath(sourcePath)
        script.writeFile file: destinationPath, text: script.libraryResource(sourcePath)
        return destinationPath
    }

    String convertHTMLToString(def filename) {
		def emailContent = script.readFile encoding: 'UTF-8', file: "${filename}"
		return emailContent;
	}

	String getEmailContent(def environment) {
		convertHTMLToString("templates/email_template.html").replace("%MAIN_CONTENT%", "The Build is Deployed in the " +
				environment.toUpperCase() + " server.").replace("%SUB_CONTENT%", "<br/>Pipeline URL: " +
				"${script.env.JOB_URL}").replace("%TEAM%", "Build Team.");
	}
    
    // void sendNotification() {
    //     String emailContent = getEmailContent("Staging")
    //     script.mail(
    //         from: 'rahul.a@contus.in',
    //         to: 'rahula7200@gmail.com',
    //         cc: 'rahul.a@contus.in',
    //         subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' + '$BUILD_STATUS',
    //         body: emailContent
    //     )
    // }
}
    // void sendMail(String status, String from, String to, String cc, String subject, String body) {
    //     script.mail(
    //         from: from,
    //         to: to,
    //         cc: cc,
    //         subject: "${status} - ${subject}",
    //         body: body
    //     )
    // }
