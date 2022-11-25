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

    GitHelper(def script) {
        this.script = script
    }

    def version(def script){
        script.sh """
            ${git} --version
        """
    }

}
