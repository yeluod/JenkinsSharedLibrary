package com.deploy.helper

import com.deploy.property.Tools

/**
 * NpmHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
class NpmHelper extends NodeHelper {

    /**
     * 构造函数
     */
    NpmHelper(script, npm) {
        super.script = script
        super.tool = npm
    }

    /**
     * 构造函数
     */
    NpmHelper(script, npm, registry) {
        super.script = script
        super.tool = npm
        super.registry = registry
    }
}
