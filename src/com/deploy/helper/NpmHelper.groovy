package com.deploy.helper
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
    NpmHelper(script) {
        super(script)
    }

    /**
     * 构造函数
     */
    NpmHelper(script, String npm) {
        super(script, npm)
    }

    /**
     * 构造函数
     */
    NpmHelper(script, String npm, String registry) {
        super(script, npm, registry)
    }
}
