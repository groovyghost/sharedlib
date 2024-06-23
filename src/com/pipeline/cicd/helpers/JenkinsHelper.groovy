package com.pipeline.cicd.helpers

import java.io.File
import groovy.text.StreamingTemplateEngine


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

    String getEmailContent(def url) {
		convertHTMLToString("email_template.html")
                .replace('${subject}', "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}")
                .replace('${jobName}', script.env.JOB_NAME)
                .replace('${buildNumber}', script.env.BUILD_NUMBER)
                .replace('${content}', "Pipeline ${script.env.JOB_NAME} build ${script.env.BUILD_NUMBER} failed.")
                .replace('${buildUrl}', script.env.BUILD_URL);
        
    }

def renderTemplate(input, variables) {
  def engine = new StreamingTemplateEngine()
  return engine.createTemplate(input).make(variables).toString()
}

}
