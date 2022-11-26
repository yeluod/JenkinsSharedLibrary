package com.deploy.helper

import com.deploy.property.Tools

/**
 * NpmHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
class YarnHelper extends NodeHelper {

    /**
     * 构造函数
     */
    YarnHelper(script, yarn) {
        super.script = script
        super.tool = yarn
    }

    /**
     * 构造函数
     */
    YarnHelper(script, yarn, registry) {
        super.script = script
        super.tool = yarn
        super.registry = registry
    }
}
