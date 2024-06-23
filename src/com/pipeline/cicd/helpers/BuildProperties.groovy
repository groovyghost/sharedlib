package com.pipeline.cicd.helpers

import com.pipeline.cicd.Constant

/**
 * Helper class for reading and parsing build properties.
 */
class BuildProperties implements Serializable {

    def script

    // File path for build properties file
    def BUILD_PROP_FILEPATH = "build.properties"

    BuildProperties(Object script) {
        this.script = script
    }

    /**
     * Reads build properties from a file and sets the service name constant.
     */
    void readBuildProperties() {
        def buildProperties = script.readFile encoding: 'UTF-8', file: "${BUILD_PROP_FILEPATH}"

        Properties propBuild = new Properties();
        propBuild.load(new StringReader(buildProperties));

        Constant.SERVICE_NAME = propBuild.getProperty("build.service.name")
        Constant.SERVICE_NAME = propBuild.getProperty("build.service.name")
    }
}

