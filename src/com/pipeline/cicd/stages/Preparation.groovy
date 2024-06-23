package com.pipeline.cicd.stages

import com.pipeline.cicd.helpers.JenkinsHelper
import com.pipeline.cicd.Constant

/**
 * Represents a stage in the pipeline that prepares the environment for the build.
 */
public class Preparation extends AbstractStage {

    Preparation(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Preparation', jenkinsHelper)
    }

    @Override
    void execute() {
        script.node(Constant.NODE) {
            script.stage(stageName) {
                // Check out the source code
                script.checkout script.scm

                // Copy the required files from the global library
                List<String> files = ['config.json', 'utils.sh']
                if (files != null) {
                    for (String file : files) {
                        if (file != null) {
                            // Copy the file from the global library to the workspace
                            String scriptPath = jenkinsHelper.copyGlobalLibraryScript(file)
                        }
                    }
                }
            }
        }
    }
}
