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

    DockerHelper(script, Tools tools) {
        this.script = script
        this.docker = tools.docker
    }

    @Override
    void version() {
        this.script.sh "${this.docker} -v"
    }
}
