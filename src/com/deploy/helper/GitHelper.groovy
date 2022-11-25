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

    /**
     * 检出分支
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     */
    def checkOut(String repoUrl, String branch) {
        checkParams(repoUrl, branch)
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

    /**
     * 拉取代码
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     */
    def pullCode(String repoUrl, String branch) {
        checkParams(repoUrl, branch)
        this.script.git(
                branch: "${branch}",
                credentialsId: "${this.credentialId}",
                url: "${repoUrl}"
        )
    }

    /**
     * 修建分支
     *
     * 由于参数化构建会使用一些三方插件，获取远程分支名称会出现 refs/heads origin
     *
     * refs/heads/dev > dev
     * origin/dev > dev
     *
     * @param branch {@link String} 分支
     */
    private static def trimBranch(String branch) {
        if (branch.contains('/')) {
            String[] array = branch.split('/')
            return array[array.length - 1]
        }
        return branch
    }

    /**
     * 校验输入参数
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     */
    private static void checkParams(String repoUrl, String branch) {
        Assert.isNotEmpty(repoUrl, '输入仓库地址为空')
        Assert.isNotEmpty(branch, '输入分支为空')
    }
}
