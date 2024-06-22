package com.pipeline.cicd.helpers

import java.io.File

class JenkinsHelper implements Serializable {

    private final script

    JenkinsHelper(script) {
        this.script = script
    }

    String createTemporaryPath(String path) {
        String temporaryDirectory = script.pwd(tmp: true)
        return temporaryDirectory + File.separator + new File(path).getName()
    }

    String copyGlobalLibraryScript(String sourcePath, String destinationPath = null) {
        destinationPath = destinationPath ?: createTemporaryPath(sourcePath)
        script.writeFile file: destinationPath, text: script.libraryResource(sourcePath)
        return destinationPath
    }
}
