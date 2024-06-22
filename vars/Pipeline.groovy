package com.pipeline.cicd

import org.jenkinsci.plugins.workflow.cps.DSL

class PipelineBuild implements Serializable {

    def execute() {
    node {
    stage('Example') {
        if (env.BRANCH_NAME == 'master') {
            echo 'I only execute on the master branch'
        } else {
            echo 'I execute elsewhere'
        }
    }
}
   }
}