package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
// import com.pipeline.cicd.helpers.BuildProperties
import com.pipeline.cicd.helpers.JenkinsHelper

/**
 * In this stage source code clone or checkout is done.
 * So this stage is called as "Test".
 * This is initial stage of the project.
 */
public class Test extends AbstractStage {

	Test(Object script, JenkinsHelper jenkinsHelper) {
		super(script, 'Test', jenkinsHelper)
	}

	@Override
	void execute() {
		def constant = new Constant()
		script.stage(stageName) {
			def PROJECT_REPO_BRANCH = "${script.env.BRANCH_NAME}"
 			loadLinuxScript(name: 'test.sh')
    		sh "bash test.sh ${script.env.BRANCH_NAME} ${script.env.BRANCH_NAME}"
			script.sh returnStdout: true, script:"echo ${script.env.SONAR_HOST}"
			script.sh('resources/test.sh')
		}
	}
}

