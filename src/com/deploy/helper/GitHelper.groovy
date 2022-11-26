package com.deploy.helper

import cn.hutool.core.lang.Assert
import cn.hutool.core.util.StrUtil
import com.deploy.helper.param.GitToolParam

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
@SuppressWarnings('unused')
class GitHelper extends BaseHelper {

    def script

    GitToolParam param

    GitHelper(script) {
        Assert.notNull(script, '当前脚本不能为空')
        this.script = script
    }

    GitHelper(script, String git, String credential) {
        Assert.notNull(script, '当前脚本不能为空')
        Assert.notBlank(git, 'Git 可执行文件配置不能为空')
        Assert.notBlank(credential, 'Git 凭证不能为空')
        this.script = script
        this.param.git = git
        this.param.credential = credential
    }

    @Override
    void version() {
        Assert.notNull(this.script, '当前脚本不能为空')
        Assert.notBlank(this.param.git, 'Git 可执行文件配置不能为空')
        this.script.sh "${this.param.git} --version"
    }

    /**
     * 检出分支
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     */
    def checkOut(String repoUrl, String branch) {
        this.checkOut(repoUrl, branch, this.param.credential)
    }

    /**
     * 检出分支
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     * @param credential {@link String} 凭证
     */
    def checkOut(String repoUrl, String branch, String credential) {
        checkParam(repoUrl, branch)
        branch = trimBranch(branch)
        this.script.println StrUtil.format('当前拉取分支为 -> {}', branch)
        this.script.checkout([$class           : 'GitSCM',
                              gitTool          : 'Default',
                              branches         : [[name: "*/${branch}"]],
                              extensions       : [[$class: 'CleanBeforeCheckout']],
                              userRemoteConfigs: [[credentialsId: "${credential}",
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
        this.pullCode(repoUrl, branch, this.param.credential)
    }

    /**
     * 拉取代码
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     * @param credential {@link String} 凭证
     */
    def pullCode(String repoUrl, String branch, String credential) {
        checkParam(repoUrl, branch)
        this.script.git(
                branch: "${branch}",
                credentialsId: "${credential}",
                url: "${repoUrl}"
        )
    }

    /**
     * 校验参数
     *
     * @param repoUrl {@link String} 仓库地址
     * @param branch {@link String} 分支
     */
    private void checkParam(String repoUrl, String branch) {
        Assert.notBlank(repoUrl, '输入仓库地址为空')
        Assert.notBlank(branch, '输入分支为空')
        Assert.notNull(this.script, '当前脚本不能为空')
        Assert.notBlank(this.param.git, 'Git 可执行文件配置不能为空')
        Assert.notBlank(this.param.credential, 'Git 凭证不能为空')
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

}
