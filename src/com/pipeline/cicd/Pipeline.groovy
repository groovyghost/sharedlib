package com.pipeline.cicd

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.stages.*
import com.pipeline.cicd.stages.UAT.*
import org.jenkinsci.plugins.workflow.cps.DSL

class Pipeline implements Serializable {

    // def script

    // def stages = []

    // DSL steps


    // JenkinsHelper jenkinsHelper

    // static builder(script, DSL steps) {
    //     return new Builder(script, steps)
    // }

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

        def withCleanupStage() {
            stages << new Cleanup(script, jenkinsHelper)
            return this
        }

        def PipelineBuild() {
            withPreparationStage()
            if (PROJECT_REPO_BRANCH.toLowerCase() == "staging"){
                Constant.NODE = "agent2"
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

    void execute() {
        for (Stage stage : stages) {
            try {
                stage.execute()
            } catch (err) {
                throw err
                new Exception(script).handle(err)
            }
        }
    }
}

