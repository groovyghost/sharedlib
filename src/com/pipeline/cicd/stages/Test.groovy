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
				script.timeout(time: 1, unit: 'MINUTES') {
				def leadApprovalComment = script.input id: 'approve_for_Production', message: 'Approve For Deploy', ok:
						'Proceed' , parameters:
						[
								script.choice(name: 'Release to deploy?', choices: ["Yes", "No"].join
										("\n"), description: 'NO - Build will not be deploy in stage server' )
						] 
						,submitter: 'rahul'
				}
			script.echo("Approval Comment: ${leadApprovalComment}");
			}
            if (leadApprovalComment.contains("Yes")) {
				script.sh "bash test.sh ${script.env.BRANCH_NAME} working"
            	}
            else {
			 script.currentBuild.result = "ABORTED"
             script.error "Lead aborted this job"
			}
		}
	}
}