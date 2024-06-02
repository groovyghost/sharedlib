package com.contus.cd.stages

import com.contus.cd.Constant
// import com.contus.cd.helpers.BuildProperties
import com.contus.cd.helpers.JenkinsHelper

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
			def PROJECT_REPO_BRANCH = "${script.env.BRANCH_NAME}"
			script.node("${Constant.NODE}") {

						script.sh returnStdout: true, script:"echo ${script.env.SONAR_HOST}"
				script.sh('#!/bin/sh -e\n' +"echo ${script.env.BRANCH_NAME}")

				//Checkout code
				script.checkout script.scm

				// Load build properties
				def buildProperties = new BuildProperties(script)
				buildProperties.readBuildProperties()
			}
		}
	}
}

