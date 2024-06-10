package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Test extends AbstractStage {

    Test(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Test', jenkinsHelper)
    }

	@Override
	void execute() {
		script.node("${Constant.NODE}") {
			script.stage(stageName) {
			jenkinsHelper.copyGlobalLibraryScript('test.sh')
			script.timeout(time: 5, unit: 'DAYS') {
				leadApprovalComment = script.input id: 'approve_for_qa', message: 'Approve for Production Deployment', ok:
                'Proceed' , parameters: 
				[ script.choice(name: 'Deploy Production Server?', choices: ["Master", "Hotfix", "Abort"].join
				("\n"), description: 'Build Deploy from stage/hotfix branch')] ,
				submitter: 'rahul@contus.in'
				}
			script.echo("Approval Comment: ${leadApprovalComment}");
            if (leadApprovalComment.contains("Abort")) {
                script.currentBuild.result = "ABORTED"
                script.error "Lead aborted this job"
            	}
            else {
			script.sh "bash test.sh ${script.env.BRANCH_NAME} working"
				}
			}
		}
	}
}