package com.pipeline.cicd

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.stages.*
import com.pipeline.cicd.stages.UAT.*
import org.jenkinsci.plugins.workflow.cps.DSL

class Pipeline implements Serializable {

    def script

    def stages = []

    DSL steps


    JenkinsHelper jenkinsHelper

    static builder(script, DSL steps) {
        return new Builder(script, steps)
    }

    static class Builder implements Serializable {

        def stages = []

        def script

        DSL steps

        JenkinsHelper jenkinsHelper

        def PROJECT_REPO_BRANCH;

        Builder(def script, DSL steps) {
            this.script = script
            this.steps = steps
            this.jenkinsHelper = new JenkinsHelper(script)
            this.PROJECT_REPO_BRANCH = "${this.script.env.BRANCH_NAME}"
        }

        def withPreparationStage() {
            stages << new Preparation(script, jenkinsHelper)
            return this
        }

        def withUATBuildStage() {
            stages << new UATBuild(script, jenkinsHelper)
            return this
        }

        def withUATDeploymentStage() {
            stages << new UATDeployment(script, jenkinsHelper)
            return this
        }

        def withCleanupStage() {
            stages << new Cleanup(script, jenkinsHelper)
            return this
        }

        def PipelineBuild() {
            withPreparationStage()
            if (PROJECT_REPO_BRANCH.toLowerCase() == "staging"){
                Constant.NODE = "agent2"
                withUATBuildStage()
                withUATDeploymentStage()
                withCleanupStage()
            }
            return new Pipeline(this)
        }
    }

    private Pipeline(Builder builder) {
        this.script = builder.script
        this.stages = builder.stages
        this.steps = builder.steps
        this.jenkinsHelper = builder.jenkinsHelper
    }
    def SendNotification(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def color = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]'"
  def summary = "${subject} (${script.env.BUILD_URL})"
  def details = """<p>${buildStatus}: Job '${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${script.env.BUILD_URL}'>${script.env.JOB_NAME} [${script.env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  }

  // Send notifications
  // slackSend (color: colorCode, message: summary)

  // hipchatSend (color: color, notify: true, message: summary)

//   script.mail(
//       from: 'rahul.a@contus.in',
//       to: 'rahul.a@contus.in, rahula7200@gamil.com',
//       subject: subject,
//       body: details,
//       recipientProviders: [[$class: 'DevelopersRecipientProvider']]
//     )
        String emailContent = jenkinsHelper.getEmailContent("Staging")
        script.echo emailContent
        script.mail(
            from: 'rahul.a@contus.in',
            to: 'rahula7200@gmail.com',
            cc: 'rahul.a@contus.in',
            subject: '🔵 $PROJECT_NAME - Build # $BUILD_NUMBER - ' + '$BUILD_STATUS',
            body: 'hi',
            mimeType: 'text/html'
        )
}
    void execute() {
        for (Stage stage : stages) {
            try {
                stage.execute()
                echo 'This will run only if successful'
                // jenkinsHelper.sendNotification()
            } catch (err) {
                // jenkinsHelper.sendNotification()
                //SendNotification(script.currentBuild.result)
                throw err
                // new Exception(script).handle(err)
            }
        }
    }
}

