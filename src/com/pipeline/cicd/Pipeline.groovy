package com.pipeline.cicd

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.stages.*
import org.jenkinsci.plugins.workflow.cps.DSL

/**
 * Pipeline for Jenkins2 Pipeline
 *
 * @author sathishkumar@contus.in
 * @version 1.0
 */
class Pipeline implements Serializable {

    def script

    def stages = []

    DSL steps

    JenkinsHelper jenkinsHelper

    static boolean isAndroid = false

    static boolean isIOS = false

    static boolean isDebug = false

    static builder(script, DSL steps) {
        return new Builder(script, steps)
    }

    static class Builder implements Serializable {

        // Stages of pipeline
        def stages = []

        // Script object to execute the jenkins based scripts
        def script

        def approvalQAComment

        DSL steps

        // {@link JenkinsHelper}
        JenkinsHelper jenkinsHelper

        //Project repo branch name. Used to select stages to be executed which changes according to branch
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

        // def withVulnerabityStage(){
        //     stages << new VulnerabilityAnalysis(script, jenkinsHelper)
        // }

        def build() {
            return new Pipeline(this)
        }

        def PipelineBuild() {
            withPreparationStage()
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

        // Choose the node suitable for branch before execution
        chooseNode()

        // `stages.each { ... }` does not work, see https://issues.jenkins-ci.org/browse/JENKINS-26481
        for (Stage stage : stages) {

            try {
                stage.execute()
            } catch (err) {
                new Exception(script).handle(err)
            }
        }
    }

    void chooseNode() {
        def PROJECT_REPO_BRANCH = "${script.env.BRANCH_NAME}"
        if (isAndroid) {
            Constant.setNODE("Android-Build")
        } else if (isIOS) {
            Constant.setNODE("Contus-Xcode9")
        }
        else {
            Constant.setNODE("agent1")
        }
    }
}

