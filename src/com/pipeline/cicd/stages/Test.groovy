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
			script.node(Constant.NODE) {
				def deployChoice = script.input(
						id: 'approve_for_deployment',
						message: 'Approve for deployment',
						ok: 'Proceed',
						parameters: [script.choice(
								name: 'Release to deploy?',
								choices: 'Yes\nNo',
								description: 'NO - Build will not be deployed in the stage server')],
						submitter: 'rahul'
				)

				if (deployChoice.contains("Yes")) {
					jenkinsHelper.copyGlobalLibraryScript('test.sh')
					script.sh "bash test.sh ${script.env.BRANCH_NAME} working"
				}
				else {
					script.currentBuild.result = "ABORTED"
					script.error "Lead aborted this job"
				}
			}
		}
	}
}
