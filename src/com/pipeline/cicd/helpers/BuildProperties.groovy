package com.contus.cd.helpers

import com.contus.cd.Constant


class BuildProperties implements Serializable {

    def script
    def BUILD_PROP_FILEPATH = "build.properties"

    BuildProperties(Object script) {
        this.script = script
    }

     def parseJSON(def json) {
         new groovy.json.JsonSlurperClassic().parseText(json)
    }


    void readBuildProperties() {
        def buildProperties = script.readFile encoding: 'UTF-8', file: "${BUILD_PROP_FILEPATH}"

        Properties propBuild = new Properties();
        propBuild.load(new StringReader(buildProperties));

        Constant.SERVICE_NAME = propBuild.getProperty("build.service.name")
        Constant.SERVICE_NAME = propBuild.getProperty("build.service.name")
    }

}

