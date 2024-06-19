package com.pipeline.cicd.stages.UAT

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.helpers.BuildProperties

public class UATDeployment extends AbstractStage {

    UATDeployment(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Staging Deployment', jenkinsHelper)
    }

    @Override
    void execute() {
        script.timeout(time: 1, unit: 'DAYS') {
            String deployChoice = script.input id: 'approveForDeployment', message: 'Approve for deployment',
                    parameters: [script.choice(name: 'Deploy to Staging?', choices: 'Yes\nNo')], submitter: Constant.AUTHOR
            script.stage(stageName) {
                script.node(Constant.NODE) {
                    def buildProperties = new BuildProperties(script)
                    buildProperties.readBuildProperties()
                    if (deployChoice == 'Yes') {
                        //script.withEnv(["BRANCH_NAME=${script.env.BRANCH_NAME}","MYENV=rahul"]){}
                        String scriptPath = jenkinsHelper.copyGlobalLibraryScript("deploy-docker.sh")
                        script.sh("bash ${scriptPath} ${Constant.SERVICE_NAME}")
                        }
                    } else if (deployChoice == 'No'){
                        script.currentBuild.result = "ABORTED"
                        script.error "Lead aborted this job"
                    } else {
                        script.error "Invalid input"
                }
            }
        }
    }
}
