def call() {
    def script = this
    def templateContent = readFile('resources/email_template.html').trim()
    
    def emailContent = templateContent.replaceAll('\\$\\{subject\\}', "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}")
                                      .replaceAll('\\$\\{jobName\\}', script.env.JOB_NAME)
                                      .replaceAll('\\$\\{buildNumber\\}', script.env.BUILD_NUMBER)
                                      .replaceAll('\\$\\{content\\}', params.content)
                                      .replaceAll('\\$\\{buildUrl\\}', script.env.BUILD_URL)

    script.emailext(
        to: Constant.OPS_MAIL,
        subject: "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}",
        body: emailContent,
        recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )
}