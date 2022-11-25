package com.deploy.property

import com.deploy.utils.GsonUtil

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
        GsonUtil.toBean(json, Credentials.class)
    }
}
