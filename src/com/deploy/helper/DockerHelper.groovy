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

    static def DOCKERFILE = 'Dockerfile'
    static def DOCKER_IGNORE = '.dockerignore'

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
     */
    void writeDockerfile() {
        this.script.println '开始写入 Dockerfile ...'
        def dockerfile = this.script.libraryResource('templates/Springboot/Dockerfile')
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee('.', "${DOCKERFILE}", dockerfile)
    }

    /**
     * 写入 dockerignore 文件
     * 防止 docker build时, Sending build context to Docker daemon 数据过大
     */
    void writeDockerignore() {
        this.script.println '开始写入 .dockerignore ...'
        def dockerIgnore = this.script.libraryResource('templates/Springboot/.dockerignore')
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee('.', "${DOCKER_IGNORE}", dockerIgnore)
    }

    void build(String imageName, String imageTag) {
        this.script.sh """
            ${this.docker} ${BUILD} ${f} ${DOCKERFILE} ${t} ${imageName}:${imageTag}
        """
    }
}
