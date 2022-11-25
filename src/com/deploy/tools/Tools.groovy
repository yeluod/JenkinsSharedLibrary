package com.deploy.tools

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

    static def read(script) {
        String json = script.libraryResource('global/config.json')
        GsonUtil.toBean(json, Tools.class)
    }
}

