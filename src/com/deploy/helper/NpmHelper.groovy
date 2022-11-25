package com.deploy.helper

import com.deploy.tools.Tools

/**
 * NpmHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
class NpmHelper extends NodeHelper {

    NpmHelper(script, Tools tools) {
        super.script = script
        super.tool = tools.npm
    }
}
