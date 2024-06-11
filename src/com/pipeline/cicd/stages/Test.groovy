package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Test extends AbstractStage {

    Test(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Test', jenkinsHelper)
    }

    @Override
    void execute() {
        script.timeout(time: 1, unit: 'MINUTES') {
            String deployChoice = script.input id: 'approveForDeployment', message: 'Approve for deployment',
                    parameters: [script.choice(name: 'Release to deploy?', choices: 'Yes\nNo')], submitter: Constant.AUTHOR

            script.stage(stageName) {
                script.node(Constant.NODE) {
                    if (deployChoice == 'Yes') {
                        scriptpath = jenkinsHelper.copyGlobalLibraryScript('test.sh')
                        script.sh "bash ${scriptpath} ${script.env.BRANCH_NAME} working"
                    } else {
                        script.currentBuild.result = "ABORTED"
                        script.error "Lead aborted this job"
                    }
                }
            }
        }
    }
}

