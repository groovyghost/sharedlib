package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
import com.pipeline.cicd.helpers.JenkinsHelper

public class Preparation extends AbstractStage {

    Preparation(Object script, JenkinsHelper jenkinsHelper) {
        super(script, 'Preparation', jenkinsHelper)
    }

    @Override
    void execute() {
		script.node("${Constant.NODE}") {
			script.stage(stageName) {
            script.checkout script.scm
            File resourcesDir = new File(this.getClass().getClassLoader().getResource("resources").getFile())
            resourcesDir.eachFile { File scriptFile ->
                String scriptPath = jenkinsHelper.copyGlobalLibraryScript(scriptFile.getName(), scriptFile.getName())
            }
            script.println ("${scriptPath}")
        	}
        }
    }
}
