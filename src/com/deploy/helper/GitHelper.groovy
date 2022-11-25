package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper extends BaseHelper{

    def script

    GitHelper(script) {
        this.script = script
        this.init()
    }

    @Override
    void init() {
        this.script.println 123123
    }
}
