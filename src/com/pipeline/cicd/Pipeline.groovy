package com.pipeline.cicd

import org.jenkinsci.plugins.workflow.cps.DSL
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.Constant
import com.pipeline.cicd.stages.*

class Pipeline implements Serializable {

    def script
    def stages = []
    DSL steps
    JenkinsHelper jenkinsHelper

    Pipeline(def script, DSL steps) {
        this.script = script
        this.steps = steps
        this.jenkinsHelper = new JenkinsHelper(script)
   }

    static Pipeline PipelineBuild(def script, DSL steps) {
        Pipeline pipeline = new Pipeline(script, steps)
        pipeline.withPreparationStage()
        if (script.env.BRANCH_NAME.toLowerCase() == "staging") {
            Constant.NODE = "agent2"
            pipeline.withCleanupStage()
        }
        return pipeline
    }

    static Pipeline TestBuild(def script, DSL steps) {
        Pipeline pipeline = new Pipeline(script, steps)
        pipeline.withPreparationStage()
        if (script.env.BRANCH_NAME.toLowerCase() == "staging") {
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
        } catch (Throwable err) {
            new Exception(script).handle(err)
        } finally {
            script.sendNotification(script, script.currentBuild.result, Constant.OPS_MAIL)
        }
    }
}

