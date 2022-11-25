package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper extends BaseHelper{

    def script
    def git

    GitHelper(script) {
        this.script = script
    }

    @Override
    def init() {
        String config = this.script.libraryResource('global/config.json')
        this.script.println config
        return this
    }
}
