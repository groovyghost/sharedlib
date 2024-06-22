package com.pipeline.cicd

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.stages.Stage
import com.pipeline.cicd.stages.Stage.Preparation
import com.pipeline.cicd.stages.Stage.Cleanup
import org.jenkinsci.plugins.workflow.cps.DSL

class Pipeline implements Serializable {

    def script

    List<Stage> stages = []

    DSL steps

    JenkinsHelper jenkinsHelper

    Pipeline(script, DSL steps) {
        this.script = script
        this.steps = steps
        this.jenkinsHelper = new JenkinsHelper(script)
        this.stages << new Preparation(script, jenkinsHelper)
        if (script.env.BRANCH_NAME.toLowerCase() == "staging"){
            Constant.NODE = "agent2"
            this.stages << new Cleanup(script, jenkinsHelper)
        }
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

