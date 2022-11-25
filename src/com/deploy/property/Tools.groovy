package com.deploy.property

import com.deploy.utils.GsonUtil

/**
 * GlobalTool
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class Tools {

    def git
    def java
    def mvn
    def npm
    def yarn
    def docker

    static def read(script) {
        String json = script.libraryResource('property/tools.json')
        GsonUtil.toBean(json, Tools.class)
    }
}
