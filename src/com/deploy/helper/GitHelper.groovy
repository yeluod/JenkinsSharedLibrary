package com.deploy.helper

import com.deploy.tools.Tools

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper extends BaseHelper{

    def script
    def git

    GitHelper(script) {
        this.script = script
    }

    @Override
    def init(Tools tools) {
        this.git = tools.git
        return this
    }

    def version() {
        this.script.sh """
            ${this.git} --version 
        """
    }
}
