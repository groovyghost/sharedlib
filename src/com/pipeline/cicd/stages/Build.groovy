package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.helpers.BuildProperties

public class Build extends AbstractStage {

    Build(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Build', jenkinsHelper)
    }

    @Override
    void execute() {
        script.stage(stageName) {
            script.node(Constant.NODE) {
                if (Constant.serviceName == null) {
                    throw new IllegalStateException("Constant.serviceName is null")
                }
                def properties = new BuildProperties(script)
                properties.readBuildProperties()
                script.sh "docker build -t ${Constant.serviceName} . --no-cache"
            }
        }
    }
}

