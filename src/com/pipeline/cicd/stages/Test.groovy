package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
// import com.pipeline.cicd.helpers.BuildProperties
import com.pipeline.cicd.helpers.JenkinsHelper

public class Test extends AbstractStage {

	Test(Object script, JenkinsHelper jenkinsHelper) {
		super(script, 'Test', jenkinsHelper)
	}

	@Override
	void execute() {
		script.stage(stageName) {
			script.node("${Constant.NODE}") {
			def PROJECT_REPO_BRANCH = "${script.env.BRANCH_NAME}"
			jenkinsHelper.copyGlobalLibraryScript("test.sh")
  			script.sh "bash test.sh ${script.env.BRANCH_NAME} working"
			}
		}
	}
}

