package com.deploy.helper

import com.deploy.tools.Tools

/**
 * MvnHelper
 *
 * @author YeLuo
 * @since 2022/11/22
 * */
class MvnHelper extends BaseHelper {

    def script
    def mvn

    MvnHelper(script, Tools tools) {
        this.script = script
        this.mvn = tools.mvn
    }

    @Override
    void version() {
        this.script.sh "${this.mvn} -v"
    }

}
