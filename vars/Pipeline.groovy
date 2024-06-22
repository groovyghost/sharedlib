package com.pipeline.cicd

import org.jenkinsci.plugins.workflow.cps.DSL

class PipelineBuild implements Serializable {

    def execute() {
        script.echo "Executing pipeline build"
        steps.sh "echo 'Hello, world!'"
   }
}