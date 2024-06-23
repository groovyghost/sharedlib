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

def call(script,buildStatus, emailRecipients) {
//  In Jenkins, the currentBuild.result attribute reflects the outcome of the build.
//  However, until an explicit status is set (such as FAILURE, UNSTABLE, or ABORTED), or until the build finishes, currentBuild.result remains null.
//  This null state essentially indicates that the build is ongoing and no issues have been detected that would set a failure or unstable status.
//  If currentBuild.result is still null when the build completes, Jenkins implicitly treats this as a successful build (SUCCESS).
  buildStatus = buildStatus ?: 'SUCCESS'


    try {

        def icon = "✅"
        def statusSuccess = true
        def hasArtifacts = false

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
            subject: "${icon} [ ${script.env.JOB_NAME} ] [ Build - ${script.env.BUILD_NUMBER} ] - ${buildStatus} ",
            body: body,
            mimeType: 'text/html',
            recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']] )

    } catch (e){
        println "ERROR SENDING EMAIL ${e}"
    }
}