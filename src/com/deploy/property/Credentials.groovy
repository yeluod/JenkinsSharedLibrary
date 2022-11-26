package com.deploy.property

import cn.hutool.json.JSONUtil

/**
 * Credentials
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
class Credentials {

    def git
    def harbor

    static def read(script) {
        String json = script.libraryResource('property/credentials.json')
        JSONUtil.toBean(json, Credentials.class)
    }
}
