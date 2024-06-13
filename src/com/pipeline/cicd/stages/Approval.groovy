package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Approval extends AbstractStage {

    Approval(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Approval', jenkinsHelper)
    }

    @Override
    void execute() {
        script.timeout(time: 10, unit: 'MINUTES') {
            String deployChoice = script.input id: 'approveForDeployment', message: 'Approve for deployment',
                    parameters: [script.choice(name: 'Release to deploy?', choices: 'Yes\nNo')], submitter: Constant.AUTHOR

            script.stage(stageName) {
                script.node(Constant.NODE) {
                    if (deployChoice == 'Yes') {
                        script.withEnv(["BRANCH_NAME=${script.env.BRANCH_NAME}","MYENV=rahul"])
                        String scriptpath = jenkinsHelper.copyGlobalLibraryScript('test.sh')
                        script.sh("bash ${scriptpath} ${script.env.BRANCH_NAME} working")
                    } else {
                        script.currentBuild.result = "ABORTED"
                        script.error "Lead aborted this job"
                    }
                }
            }
        }
    }
}

