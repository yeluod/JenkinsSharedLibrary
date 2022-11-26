package com.deploy.helper

import com.deploy.property.Tools

/**
 * DockerHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
class DockerHelper extends BaseHelper {

    def script
    def docker
    def dockerfilePath

    static def DOCKERFILE_NAME = 'Dockerfile'
    static def DOCKER_IGNORE = '.dockerignore'

    static def CURRENT_DIRECTORY = '.'

    static def BUILD = 'build'
    static def PUSH = 'push'

    static def f = '-f'
    static def t = '-t'


    /**
     * 构造函数
     */
    DockerHelper(script, Tools tools) {
        this.script = script
        this.docker = tools.docker
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.docker} -v"
    }

    /**
     * 写入 Dockerfile 文件
     *
     * @param source {@link String} 资源文件
     * @return {@link String} 输出文件路径
     */
    void writeDockerfile(String source) {
        this.writeDockerfile(source, CURRENT_DIRECTORY)
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
        this.writeDockerfile(source, CURRENT_DIRECTORY, DOCKERFILE_NAME, map)
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
        this.dockerfilePath = outputPath + '/' + outputName
    }

    /**
     * 写入 dockerignore 文件
     * 防止 docker build时, Sending build context to Docker daemon 数据过大
     */
    void writeDockerignore(String source) {
        this.writeDockerignore(source, CURRENT_DIRECTORY)
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

    void build(String imageName, String imageTag) {
        this.script.sh """
            ${this.docker} ${BUILD} ${f} ${this.dockerfilePath} ${t} ${imageName}:${imageTag} .
        """
    }
}
