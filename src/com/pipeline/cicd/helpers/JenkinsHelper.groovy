package com.pipeline.cicd.helpers

import java.io.File

/**
 * Helper class for Jenkins operations.
 */
class JenkinsHelper implements Serializable {

    private final script

    JenkinsHelper(script) {
        this.script = script
    }

    /**
     * Creates a temporary path by appending the name of the provided path to the Jenkins temporary directory.
     *
     * @param path The path to create a temporary path for.
     * @return The temporary path.
     */
    String createTemporaryPath(String path) {
        String temporaryDirectory = script.pwd(tmp: true)
        return temporaryDirectory + File.separator + new File(path).getName()
    }

    /**
     * Copies a script from the global Jenkins library to the specified destination path.
     * If no destination path is provided, a temporary path is created.
     *
     * @param sourcePath The path of the script to copy in the global Jenkins library.
     * @param destinationPath The destination path for the copied script (optional).
     * @return The path of the copied script.
     */
    String copyGlobalLibraryScript(String sourcePath, String destinationPath = null) {
        destinationPath = destinationPath ?: createTemporaryPath(sourcePath)
        script.writeFile file: destinationPath, text: script.libraryResource(sourcePath)
        return destinationPath
    }
}

