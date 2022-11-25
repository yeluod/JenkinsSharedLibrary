package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper extends BaseHelper{

    def script
    def git

    GitHelper(def script) {
        this.script = script
        this.init()
    }

    @NonCPS
    @Override
    def init() {
        this.script.sh """
            echo hello
        """
    }

    def version(def script){
        script.sh """
            ${git} --version
        """
    }

}
