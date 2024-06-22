def call(script, Map params) {
    def template = readFileFromWorkspace('resources/email_template.html').trim()
    
    def emailContent = template.replaceAll('\\$\\{subject\\}', params.subject)
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
