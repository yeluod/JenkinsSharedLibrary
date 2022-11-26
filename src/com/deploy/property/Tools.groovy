package com.deploy.property

import cn.hutool.json.JSONUtil

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
        JSONUtil.toBean(json, Tools.class)
    }
}

