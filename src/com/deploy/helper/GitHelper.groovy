package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper {

    def script
    def git

    GitHelper(def script, String gitPath) {
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
