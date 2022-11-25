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
    YarnHelper(script, Tools tools) {
        super.script = script
        super.tool = tools.yarn
    }

}
