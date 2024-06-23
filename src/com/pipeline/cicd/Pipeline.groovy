package com.pipeline.cicd

import org.jenkinsci.plugins.workflow.cps.DSL
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.helpers.Exception
import com.pipeline.cicd.Constant
import com.pipeline.cicd.stages.*

/**
 * Represents a pipeline with multiple stages and steps.
 */
class Pipeline implements Serializable {

    /**
     * The Jenkins script object.
     */
    def script

    /**
     * The list of stages in the pipeline.
     */
    def stages = []

    /**
     * The Jenkins workflow DSL steps.
     */
    DSL steps

    /**
     * The Jenkins helper object.
     */
    JenkinsHelper jenkinsHelper

    /**
     * Constructor for Pipeline.
     *
     * @param script The Jenkins script object.
     * @param steps The Jenkins workflow DSL steps.
     */
    Pipeline(def script, DSL steps) {
        this.script = script
        this.steps = steps
        this.jenkinsHelper = new JenkinsHelper(script)
   }

    /**
     * Creates a new Pipeline object with a Preparation stage and (optionally) a Cleanup stage if the branch is "staging".
     *
     * @param script The Jenkins script object.
     * @param steps The Jenkins workflow DSL steps.
     * @return The created Pipeline object.
     */
    static Pipeline PipelineBuild(def script, DSL steps) {
        Pipeline pipeline = new Pipeline(script, steps)
        pipeline.withPreparationStage()
        if (script.env.BRANCH_NAME.toLowerCase() == "staging") {
            Constant.NODE = "agent2"
            pipeline.withCleanupStage()
        }
        return pipeline
    }

    /**
     * Creates a new Pipeline object with a Preparation stage and (optionally) a Cleanup stage if the branch is "staging".
     *
     * @param script The Jenkins script object.
     * @param steps The Jenkins workflow DSL steps.
     * @return The created Pipeline object.
     */
    static Pipeline TestBuild(def script, DSL steps) {
        Pipeline pipeline = new Pipeline(script, steps)
        pipeline.withPreparationStage()
        if (script.env.BRANCH_NAME.toLowerCase() == "staging") {
            Constant.NODE = "agent2"
            pipeline.withCleanupStage()
        }
        return pipeline
    }

    /**
     * Following are the stages that can be added to the pipeline.
     *
     * @return The updated Pipeline object.
     */
    Pipeline withPreparationStage() {
        stages << new Preparation(script, jenkinsHelper)
        return this
    }

    Pipeline withCleanupStage() {
        stages << new Cleanup(script, jenkinsHelper)
        return this
    }

    /**
     * Executes the pipeline stages.
     */
    void execute() {
        try {
            for (Stage stage : stages) {
                stage.execute()
            }
        } catch (Throwable err) {
            new Exception(script).handle(err)
        } finally {
            // Send a notification email with the build status
            script.MailNotification(script, script.currentBuild.result, Constant.OPS_MAIL)
        }
    }
}

