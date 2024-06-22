// vars/sendNotification.groovy

def call(script, Throwable err) {
        def templateContent = readFile('resources/email_template.html').trim()
    
    def emailContent = templateContent.replaceAll('\\$\\{subject\\}', "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}")
                                      .replaceAll('\\$\\{jobName\\}', script.env.JOB_NAME)
                                      .replaceAll('\\$\\{buildNumber\\}', script.env.BUILD_NUMBER)
                                      .replaceAll('\\$\\{content\\}', "Pipeline ${script.env.JOB_NAME} build ${script.env.BUILD_NUMBER} succeeded.")
                                      .replaceAll('\\$\\{buildUrl\\}', script.env.BUILD_URL)
    script.echo(emailContent)
    script.echo "Sending notification due to error: ${err.message}"
    // Sending an email notification using the emailext step
    script.emailext(
        to: Constant.OPS_MAIL, // Add the static email address here
        subject: "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}",
        body: """<p>Pipeline <b>${script.env.JOB_NAME}</b> build <b>${script.env.BUILD_NUMBER}</b> failed.</p>
                <p>Error: ${err.message}</p>
                <p>Check the details at: ${script.env.BUILD_URL}</p>""",
        recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )
}
