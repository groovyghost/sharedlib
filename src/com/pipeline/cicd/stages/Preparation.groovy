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
                try {
                    script.checkout script.scm
                    File resourcesDir = new File(
                            getClass().getClassLoader().getResource("resources").getFile())
                    if (resourcesDir != null) {
                        resourcesDir.eachFile { File scriptFile ->
                            String scriptPath = jenkinsHelper.copyGlobalLibraryScript(
                                    scriptFile.getName(), scriptFile.getName())
                            if (scriptPath != null) {
                                script.println(scriptPath)
                            }
                        }
                    }
                } catch (Exception e) {
                    script.error("Error during Preparation stage: ${e.getMessage()}")
                }
            }
        }
    }
}

