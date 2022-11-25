package com.deploy.helper

import com.deploy.property.Credentials
import com.deploy.property.Tools
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
    def credentialId

    GitHelper(script, Tools tools, Credentials credentials) {
        this.script = script
        this.git = tools.git
        this.credentialId = credentials.git
    }

    @Override
    void version() {
        this.script.sh "${this.git} --version"
    }

    def checkOut(String repoUrl, String branch) {
        Assert.isNotEmpty(repoUrl, '输入仓库地址为空')
        Assert.isNotEmpty(branch, '输入分支为空')
        branch = trimBranch(branch)
        this.script.println String.format('当前拉取分支为 -> {%s}', branch)
        this.script.checkout([$class           : 'GitSCM',
                           gitTool          : 'Default',
                           branches         : [[name: "*/${branch}"]],
                           extensions       : [[$class: 'CleanBeforeCheckout']],
                           userRemoteConfigs: [[credentialsId: "${this.credentialId}",
                                                url          : "${repoUrl}"]]]
                )
    }

    private static def trimBranch(String branch) {
        Optional.ofNullable(branch)
                .filter(item -> item.contains('/'))
                .map(item -> {
                    String[] array = item.split('/')
                    return array[array.length - 1]
                })
                .orElse(branch)
    }

}
