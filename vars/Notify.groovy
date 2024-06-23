#!groovy

package workflowlibs.manager;

import groovy.text.StreamingTemplateEngine

/**
 * This method returns a string with the template filled with groovy variables
 */
def emailTemplate(params) {

    def fileName = "templates/email.html.groovy"
    def fileContents = libraryResource(fileName)
    def engine = new StreamingTemplateEngine()

    return engine.createTemplate(fileContents).make(params).toString()
}

/**
 * This method send an email generated with data from Jenkins
 * @param buildStatus String with job result
 * @param emailRecipients Array with emails: emailRecipients = []
 */
def call(script,buildStatus, emailRecipients) {
  buildStatus = buildStatus ?: 'SUCCESS'

    try {

        def icon = "✅"
        def statusSuccess = true
        def hasArtifacts = true

        if(buildStatus != "SUCCESS") {
            icon = "❌"
            statusSuccess = false
            hasArtifacts = false
        }

        def body = emailTemplate([
            "jenkinsText"   :   script.env.JOB_NAME,
            "jenkinsUrl"    :   script.env.BUILD_URL,
            "statusSuccess" :   statusSuccess,
            "hasArtifacts"  :   hasArtifacts,
            "downloadUrl"   :   "www.downloadurl.com"
        ]);

        script.emailext(
            to: emailRecipients,
            subject: "${icon} [ ${script.env.JOB_NAME} ] [${script.env.BUILD_NUMBER}] - ${buildStatus} ",
            body: body,
            mimeType: 'text/html',
            recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']] )

    } catch (e){
        println "ERROR SENDING EMAIL ${e}"
    }
}