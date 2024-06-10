package com.pipeline.cicd

import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.stages.Stage
import org.jenkinsci.plugins.workflow.cps.DSL

class Pipeline implements Serializable {

    private final script
    private final stages = []
    private final DSL steps
    private final JenkinsHelper jenkinsHelper

    static builder(script, DSL steps) {
        return new Builder(script, steps)
    }

    static class Builder implements Serializable {

        private final script
        private final DSL steps
        private final JenkinsHelper jenkinsHelper
        private final stages = []

        Builder(script, DSL steps) {
            this.script = script
            this.steps = steps
            this.jenkinsHelper = new JenkinsHelper(script)
        }

        Builder withPreparationStage() {
            stages << new Preparation(script, jenkinsHelper)
            return this
        }

        Builder withTestStage() {
            stages << new Test(script, jenkinsHelper)
            return this
        }

        Pipeline build() {
            return new Pipeline(script, stages, steps, jenkinsHelper)
        }
    }

    private Pipeline(script, List<Stage> stages, DSL steps, JenkinsHelper jenkinsHelper) {
        this.script = script
        this.stages = stages
        this.steps = steps
        this.jenkinsHelper = jenkinsHelper
    }

    void execute() {
        for (Stage stage : stages) {
            try {
                stage.execute()
            } catch (err) {
                new Exception(script).handle(err)
            }
        }
    }
}
