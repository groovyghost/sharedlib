/**
 * This function generates an email body using a template and the provided parameters.
 *
 * @param params a map containing the parameters for the email template
 * @return the generated email body as a String
 */
import groovy.text.StreamingTemplateEngine

def emailTemplate(params) {

    def fileName = "templates/email.html.groovy"
    def fileContents = libraryResource(fileName)
    def engine = new StreamingTemplateEngine()

    return engine.createTemplate(fileContents).make(params).toString()
}

/**
 * This function sends an email notification when a build completes.
 *
 * @param script the Jenkins script object
 * @param buildStatus the status of the build
 * @param emailRecipients the list of email recipients
 */
def call(script, buildStatus, emailRecipients) {
    // Set the build status to SUCCESS if it is still null
    buildStatus = buildStatus ?: 'SUCCESS'

    try {
        // Set the initial values for the email variables
        def icon = "✅"
        def statusSuccess = true
        def hasArtifacts = false

        // Set the values for the email variables based on the build status
        if(buildStatus != "SUCCESS") {
            icon = "❌"
            statusSuccess = false
            hasArtifacts = false
        }

        // Generate the email body using the email template and the provided parameters
        def body = emailTemplate([
            "jenkinsText"   :   script.env.JOB_NAME,
            "jenkinsUrl"    :   script.env.BUILD_URL,
            "statusSuccess" :   statusSuccess,
            "hasArtifacts"  :   hasArtifacts,
            "downloadUrl"   :   "example.com",
        ])

        script.emailext(
            to: emailRecipients,
            subject: "${icon} [ ${script.env.JOB_NAME} ] [ Build - ${script.env.BUILD_NUMBER} ] - ${buildStatus} ",
            body: body,
            mimeType: 'text/html',
            recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']] )

    } catch (e){
        println "ERROR SENDING EMAIL ${e}"
    }
}
