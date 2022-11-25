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
}
