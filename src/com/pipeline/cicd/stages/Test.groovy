package com.pipeline.cicd.stages

import com.pipeline.cicd.Constant
// import com.pipeline.cicd.helpers.BuildProperties
import com.pipeline.cicd.helpers.JenkinsHelper

public class Test extends AbstractStage {

	Test(Object script, JenkinsHelper jenkinsHelper) {
		super(script, 'Test', jenkinsHelper)
	}

	@Override
	void execute() {
		script.stage(stageName) {
			def PROJECT_REPO_BRANCH = "${script.env.BRANCH_NAME}"
			def scriptcontents = libraryResource "test.sh"    
  			writeFile file: "test.sh", text: scriptcontents 
  			sh "chmod a+x ./test.sh"
    		sh "bash test.sh ${script.env.BRANCH_NAME} ${script.env.BRANCH_NAME}"
			script.sh returnStdout: true, script:"echo ${script.env.SONAR_HOST}"
			script.sh('resources/test.sh')
		}
	}
}

