package com.deploy.helper

import com.deploy.tools.Tools

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class JavaHelper extends BaseHelper {

    def script
    def java

    JavaHelper(script) {
        this.script = script
    }

    @Override
    def init(Tools tools) {
        this.java = tools.java
        this
    }

    @Override
    void version() {
        this.script.sh """
            ${this.java} --version 
        """
    }
}
