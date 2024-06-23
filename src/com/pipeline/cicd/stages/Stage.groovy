package com.pipeline.cicd.stages

/**
 * Represents a stage in the pipeline.
 * Implementing classes should provide an implementation for the execute() method.
 */
interface Stage extends Serializable {

    void execute()
}
