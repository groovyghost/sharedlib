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

variables = [ pipelineStatus: buildStatus,
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
  script.echo "${buildStatus}"
  script.echo "${script.env.BRANCH_NAME}"
script.echo "${script.env.BRANCH_NAME}"
script.echo "${script.env.BRANCH_IS_PRIMARY}"
script.echo "${script.env.CHANGE_ID}"
script.echo "${script.env.CHANGE_URL}"
script.echo "${script.env.CHANGE_TITLE}"
script.echo "${script.env.CHANGE_AUTHOR}"
script.echo "${script.env.CHANGE_AUTHOR_DISPLAY_NAME}"
script.echo "${script.env.CHANGE_AUTHOR_EMAIL}"
script.echo "${script.env.CHANGE_TARGET}"
script.echo "${script.env.CHANGE_BRANCH}"
script.echo "${script.env.CHANGE_FORK}"
script.echo "${script.env.TAG_NAME}"
script.echo "${script.env.TAG_TIMESTAMP}"
script.echo "${script.env.TAG_UNIXTIME}"
script.echo "${script.env.TAG_DATE}"
script.echo "${script.env.JOB_DISPLAY_URL}"
script.echo "${script.env.RUN_DISPLAY_URL}"
script.echo "${script.env.RUN_ARTIFACTS_DISPLAY_URL}"
script.echo "${script.env.RUN_CHANGES_DISPLAY_URL}"
script.echo "${script.env.RUN_TESTS_DISPLAY_URL}"
script.echo "${script.env.CI}"
script.echo "${script.env.BUILD_NUMBER}"
script.echo "${script.env.BUILD_ID}"
script.echo "${script.env.BUILD_DISPLAY_NAME}"
script.echo "${script.env.JOB_NAME}"
script.echo "${script.env.JOB_BASE_NAME}"
script.echo "${script.env.BUILD_TAG}"
script.echo "${script.env.EXECUTOR_NUMBER}"
script.echo "${script.env.NODE_NAME}"
script.echo "${script.env.NODE_LABELS}"
script.echo "${script.env.WORKSPACE}"
script.echo "${script.env.WORKSPACE_TMP}"
script.echo "${script.env.JENKINS_HOME}"
script.echo "${script.env.JENKINS_URL}"
script.echo "${script.env.BUILD_URL}"
script.echo "${script.env.JOB_URL}"
script.echo "${script.env.GIT_COMMIT}"
script.echo "${script.env.GIT_PREVIOUS_COMMIT}"
script.echo "${script.env.GIT_PREVIOUS_SUCCESSFUL_COMMIT}"
script.echo "${script.env.GIT_BRANCH}"
script.echo "${script.env.GIT_LOCAL_BRANCH}"
script.echo "${script.env.GIT_CHECKOUT_DIR}"
script.echo "${script.env.GIT_URL}"
script.echo "${script.env.GIT_COMMITTER_NAME}"
script.echo "${script.env.GIT_AUTHOR_NAME}"
script.echo "${script.env.GIT_COMMITTER_EMAIL}"
script.echo "${script.env.GIT_AUTHOR_EMAIL}"
script.echo "${script.env.MERCURIAL_REVISION}"
script.echo "${script.env.MERCURIAL_REVISION_SHORT}"
script.echo "${script.env.MERCURIAL_REVISION_NUMBER}"
script.echo "${script.env.MERCURIAL_REVISION_BRANCH}"
script.echo "${script.env.MERCURIAL_REPOSITORY_URL}"

}