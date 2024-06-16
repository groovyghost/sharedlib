package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Preparation extends AbstractStage {

    Preparation(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Preparation', jenkinsHelper)
    }

    @Override
    void execute() {
        script.node(Constant.NODE) {
            script.stage(stageName) {
                script.checkout script.scm
                List<String> files = ['config.json', 'deploy-docker.sh', 'utils.sh']
                if (files != null) {
                    for (String file : files) {
                        if (file != null) {
                            jenkinsHelper.copyGlobalLibraryScript(file)
                        }
                    }
                }
                script.sh("bash deploy-docker.sh working")
            }
        }
    }
}

