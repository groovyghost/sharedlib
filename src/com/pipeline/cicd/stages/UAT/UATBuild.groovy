package com.pipeline.cicd.stages.UAT

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.helpers.BuildProperties

public class UATBuild extends AbstractStage {

    UATBuild(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'DockerBuild', jenkinsHelper)
    }

    @Override
    void execute() {
        script.stage(stageName) {
            script.node(Constant.NODE) {
                if (Constant.SERVICE_NAME == null) {
                    throw new IllegalStateException("Constant.SERVICE_NAME is null")
                }
                def properties = new BuildProperties(script)
                properties.readBuildProperties()
                if (Constant.SERVICE_NAME.equals('webchat') || Constant.SERVICE_NAME.equals('signalservice')) {
                    def envvariable = 'uat';
                    script.sh "docker build --build-arg ENVIRONMENT=${envvariable} -t ${Constant.SERVICE_NAME} . --no-cache"
                } else {
                    script.sh "docker build -t ${Constant.SERVICE_NAME} . --no-cache"
                }
            }
        }
    }
}

