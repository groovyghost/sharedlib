package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
// import com.pipeline.cicd.helpers.BuildProperties
import com.pipeline.cicd.helpers.JenkinsHelper

/**
 * In this stage source code clone or checkout is done.
 * So this stage is called as "Preparation".
 * This is initial stage of the project.
 */
public class Preparation extends AbstractStage {

	Preparation(Object script, JenkinsHelper jenkinsHelper) {
		super(script, 'Preparation', jenkinsHelper)
	}

	@Override
	void execute() {
		def constant = new Constant()
		script.stage(stageName) {
			script.node("${Constant.NODE}") {

			//Checkout code
    		script.checkout script.scm
            
            // Load build properties
			// def buildProperties = new BuildProperties(script)
			// buildProperties.readBuildProperties()
			}
		}
	}
}

