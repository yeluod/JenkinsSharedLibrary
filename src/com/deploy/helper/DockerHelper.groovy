package com.deploy.helper


import cn.hutool.core.util.StrUtil
import com.deploy.helper.param.DockerToolParam

/**
 * DockerHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
@SuppressWarnings('unused')
class DockerHelper extends BaseHelper {

    def script
    DockerToolParam param

    String dockerfilePath
    String sourceImageName
    String sourceImageTag
    String targetImageName
    String targetImageTag

    static def DOCKERFILE_NAME = 'Dockerfile'
    static def DOCKER_IGNORE = '.dockerignore'

    static def BUILD = 'build'
    static def TAG = 'tag'
    static def PUSH = 'push'
    static def RMI = 'rmi'
    static def SYSTEM_PRUNE = 'system prune -f'
    static def SYSTEM_DF = 'system df'

    static def f = '-f'
    static def t = '-t'

    /**
     * 构造函数
     */
    DockerHelper(script) {
        this.script = script
    }

    /**
     * 构造函数
     */
    DockerHelper(script, String docker) {
        this.script = script
        this.param.docker = docker
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.param.docker} -v"
    }

    /**
     * 写入 Dockerfile 文件
     *
     * @param source {@link String} 资源文件
     * @return {@link String} 输出文件路径
     */
    void writeDockerfile(String source) {
        this.writeDockerfile(source, StrUtil.DOT)
    }

    /**
     * 写入 Dockerfile 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @return {@link String} 输出文件路径
     */
    void writeDockerfile(String source, String outputPath) {
        this.writeDockerfile(source, outputPath, DOCKERFILE_NAME)
    }

    /**
     * 写入 Dockerfile 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @param outputName {@link String} 输出名称
     * @return {@link String} 输出文件路径
     */
    void writeDockerfile(String source, String outputPath, String outputName) {
        this.writeDockerfile(source, outputPath, outputName, null)
    }

    /**
     * 写入 Dockerfile 文件
     *
     * @param source {@link String} 资源文件
     * @param variable {@link Map} 需要替换的变量
     * @return {@link String} 输出文件路径
     */
    void writeDockerfile(String source, Map map) {
        this.writeDockerfile(source, StrUtil.DOT, DOCKERFILE_NAME, map)
    }

    /**
     * 写入 Dockerfile 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @param outputName {@link String} 输出名称
     * @param variable {@link Map} 需要替换的变量
     * @return {@link String} 输出文件路径
     */
    void writeDockerfile(String source, String outputPath, String outputName, Map variable) {
        this.script.println '开始写入 Dockerfile ...'
        def dockerfileSource = this.script.libraryResource(source)
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee(outputPath, outputName, dockerfileSource, variable)
        // 赋值 dockerfile 文件路径
        this.dockerfilePath = outputPath + StrUtil.SLASH + outputName
    }

    /**
     * 写入 dockerignore 文件
     * 防止 docker build时, Sending build context to Docker daemon 数据过大
     */
    void writeDockerignore(String source) {
        this.writeDockerignore(source, StrUtil.DOT)
    }

    /**
     * 写入 dockerignore 文件
     * 防止 docker build时, Sending build context to Docker daemon 数据过大
     */
    void writeDockerignore(String source, String outputPath) {
        this.script.println '开始写入 .dockerignore ...'
        def dockerIgnoreSource = this.script.libraryResource(source)
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee(outputPath, DOCKER_IGNORE, dockerIgnoreSource)
    }

    /**
     * build
     *
     * @param name {@link String} 名称
     * @param tag {@link String} 版本
     */
    void build(String name, String tag) {
        this.build(name, tag, StrUtil.DOT)
    }

    /**
     * build
     *
     * @param name {@link String} 名称
     * @param tag {@link String} 版本
     * @param path {@link String} 工作路径
     */
    void build(String name, String tag, String path) {
        this.script.sh """
            ${this.param.docker} ${BUILD} ${f} ${this.dockerfilePath} ${t} ${name}:${tag} ${path}
        """
        this.sourceImageName = name
        this.sourceImageTag = tag
    }

    /**
     * tag
     *
     * @param targetName {@link String} 目标名称
     */
    void tag(String targetName) {
        this.tag(targetName, this.sourceImageTag)
    }

    /**
     * tag
     *
     * @param targetName {@link String} 目标名称
     * @param targetTag {@link String} 目标版本
     */
    void tag(String targetName, String targetTag) {
        this.script.sh """
            ${this.param.docker} ${TAG} ${this.sourceImageName}:${this.sourceImageTag} ${targetName}:${targetTag}
        """
        this.targetImageName = targetName
        this.targetImageTag = targetTag
    }

    /**
     * 推送镜像
     */
    void push() {
        def image = this.getImage()
        this.script.sh "${this.param.docker} ${PUSH} ${image}"
    }

    def isLogin(String host) {
        try {
            this.script.sh "cat ~/.docker/config.json | grep ${host}"
            return true
        } catch (Throwable ignored) {
            return false
        }
    }

    /**
     * 登陆
     * @param host {@link String} 仓库地址
     * @param credentialsId {@link String} 凭证ID
     */
    void login(String host, String credentialsId) {
        this.script
                .withCredentials([this.script.usernamePassword(
                        credentialsId: "${credentialsId}",
                        passwordVariable: 'IMAGES_REGISTRY_PASSWORD',
                        usernameVariable: 'IMAGES_REGISTRY_USERNAME')]) {
                    this.script.sh """
                        ${param.docker} login ${host} -u ${this.script.IMAGES_REGISTRY_USERNAME} -p ${this.script.IMAGES_REGISTRY_PASSWORD}
                    """
                }
    }

    /**
     * 获取最终镜像
     */
    String getImage() {
        StrUtil.isNotBlank(this.targetImageName)
                ? "${this.targetImageName}:${this.targetImageTag}"
                : "${this.sourceImageName}:${this.sourceImageTag}"
    }

    /**
     * 清除本地镜像
     */
    void rmi() {
        this.script.sh "${this.param.docker} ${RMI} ${this.sourceImageName}:${this.sourceImageTag}"
        if (StrUtil.isNotBlank((String) this.targetImageName)) {
            this.script.sh "${this.param.docker} ${RMI} ${this.targetImageName}:${this.targetImageTag}"
        }
    }

    /**
     * 显示磁盘使用情况
     */
    void systemDf() {
        this.script.sh "${this.param.docker} ${SYSTEM_DF}"
    }

    /**
     * 删除未使用的数据
     * - all stopped containers
     * - all networks not used by at least one container
     * - all dangling images
     * - all dangling build cache
     */
    void systemPrune() {
        this.script.sh "${this.param.docker} ${SYSTEM_PRUNE}"
    }
}
