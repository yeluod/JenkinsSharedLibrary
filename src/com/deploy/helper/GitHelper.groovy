package com.deploy.helper

import com.deploy.tools.Tools
import com.deploy.utils.Assert

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

    void checkOut(def branch, def credentials, def repoUrl) {
        Assert.isNotEmpty(branch, '输入分支为空')
        Assert.isNotEmpty(credentials, '输入凭证为空')
        Assert.isNotEmpty(credentials, '输入仓库地址为空')
    }
}
