package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper {

    private final def script
    private final def git

    GitHelper(Object script, String gitPath) {
        this.script = script
        this.git = gitPath
        this.version()
    }

    void version(){
        this.script.sh """
            ${git} --version
        """
    }

}
