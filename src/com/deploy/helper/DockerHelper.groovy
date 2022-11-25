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

    void writeDockerfile() {
        this.script.println '开始写入 Dockerfile ...'
        def dockerfile = this.script.libraryResource('templates/Springboot/Dockerfile')
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee('.', "${DOCKERFILE}", dockerfile)
    }

    void writeDockerignore() {
        this.script.println '开始写入 .dockerignore ...'
        def dockerIgnore = this.script.libraryResource('templates/Springboot/.dockerignore')
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee('.', "${DOCKER_IGNORE}", dockerIgnore)
    }

}
