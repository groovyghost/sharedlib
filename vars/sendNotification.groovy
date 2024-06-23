import groovy.text.StreamingTemplateEngine

def renderTemplate(input, variables) {
  def engine = new StreamingTemplateEngine()
  return engine.createTemplate(input).make(variables).toString()
}

def call(script, String buildStatus = 'STARTED', String recipient, String project_name) {
  // build status of null means successful
  buildStatus = buildStatus ?: 'SUCCESS'

  // // Default values
  // def colorName = 'RED'
  // def colorCode = '#FF0000'
  def subject = "Pipeline Failed: ${script.env.JOB_NAME} ${script.env.BUILD_NUMBER}"
  // def summary = "${subject} (${env.BUILD_URL})"
  // def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
  //   <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  // // Override default values based on build status
  // if (buildStatus == 'STARTED') {
  //   color = 'YELLOW'
  //   colorCode = '#FFFF00'
  // } else if (buildStatus == 'SUCCESS') {
  //   color = 'GREEN'
  //   colorCode = '#00FF00'
  // } else {
  //   color = 'RED'
  //   colorCode = '#FF0000'
  // }

variables = [ PIPELINE_STATUS: buildStatus,
              PIPELINE_URL: script.env.BUILD_URL,
              COMMIT_AUTHOR: script.env.CHANGE_AUTHOR,
              COMMIT_NAME: script.env.GIT_COMMIT,
              BRANCH_NAME: script.env.BRANCH_NAME,
              PROJECT_NAME: project_name,
              BUILD_NUMBER: script.env.BUILD_NUMBER
]
template = script.libraryResource('templates/email.html.groovy')
report = renderTemplate(template, variables)
  script.emailext (
      to: recipient,
      subject: subject,
      body: report,
      recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )
  script.echo "${report}"
}