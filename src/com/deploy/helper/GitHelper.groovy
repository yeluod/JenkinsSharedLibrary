package com.deploy.helper

import com.deploy.tools.Tools

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper extends BaseHelper {

    def script
    def git

    GitHelper(script, Tools tools) {
        this.script = script
        this.git = tools.git
    }

    @Override
    void version() {
        this.script.sh "${this.git} --version"
    }
}
