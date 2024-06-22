// vars/sendNotification.groovy

def call(script, Throwable err) {
    script.echo "Sending notification due to error: ${err.message}"
    // Sending an email notification using the emailext step
    script.emailext(
        to: 'staticemail@example.com', // Add the static email address here
        subject: "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}",
        body: """<p>Pipeline <b>${script.env.JOB_NAME}</b> build <b>${script.env.BUILD_NUMBER}</b> failed.</p>
                <p>Error: ${err.message}</p>
                <p>Check the details at: ${script.env.BUILD_URL}</p>""",
        recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )
}
