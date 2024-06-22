def call(Map params) {
    def script = this
    def templateContent = readFile(params.templatePath).trim()
    
    def emailContent = templateContent.replaceAll('\\$\\{subject\\}', params.subject)
                                      .replaceAll('\\$\\{jobName\\}', params.jobName)
                                      .replaceAll('\\$\\{buildNumber\\}', params.buildNumber)
                                      .replaceAll('\\$\\{content\\}', params.content)
                                      .replaceAll('\\$\\{buildUrl\\}', params.buildUrl)

    script.emailext(
        to: params.to,
        subject: params.subject,
        body: emailContent,
        recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )
}
