package com.pipeline.cicd

class PipelineBuild implements Serializable {

    def execute() {
        // Your pipeline build logic goes here
        // You can access the script, steps, and jenkinsHelper objects from the Pipeline class
        // For example:
        script.echo "Executing pipeline build"
        steps.sh "echo 'Hello, world!'"
   }
}