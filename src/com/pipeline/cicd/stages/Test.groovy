package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Test extends AbstractStage {

    Test(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Test', jenkinsHelper)
    }

	@Override
	void execute() {
		script.stage(stageName) {
			script.node("${Constant.NODE}") {
			jenkinsHelper.copyGlobalLibraryScript('test.sh')
  			script.sh "bash test.sh ${script.env.BRANCH_NAME} working"
			}
		}
	}
}

