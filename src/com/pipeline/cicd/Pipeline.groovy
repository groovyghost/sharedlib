package com.pipeline.cicd

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.helpers.MailNotification
import com.pipeline.cicd.stages.*
import org.jenkinsci.plugins.workflow.cps.DSL

class Pipeline implements Serializable {

    def script
    def stages = []
    DSL steps
    JenkinsHelper jenkinsHelper
    def PROJECT_REPO_BRANCH

    Pipeline(def script, DSL steps) {
        this.script = script
        this.steps = steps
        this.jenkinsHelper = new JenkinsHelper(script)
        this.PROJECT_REPO_BRANCH = "${this.script.env.BRANCH_NAME}"
    }

    static Pipeline PipelineBuild(def script, DSL steps) {
        Pipeline pipeline = new Pipeline(script, steps)
        pipeline.withPreparationStage()
        if (pipeline.PROJECT_REPO_BRANCH.toLowerCase() == "staging") {
            Constant.NODE = "agent2"
            pipeline.withCleanupStage()
        }
        return pipeline
    }

    Pipeline withPreparationStage() {
        stages << new Preparation(script, jenkinsHelper)
        return this
    }

    Pipeline withCleanupStage() {
        stages << new Cleanup(script, jenkinsHelper)
        return this
    }

    void execute() {
        try {
            for (Stage stage : stages) {
                stage.execute()
            }
        } catch (Exception error) {
            new Exception(script).handle(error)
        } finally {
            script.echo script.currentBuild.result
            sendNotifications()
        }
    }

}
