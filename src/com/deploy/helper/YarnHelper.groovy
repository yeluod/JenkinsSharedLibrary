package com.deploy.helper
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
    YarnHelper(script) {
        super(script)
    }

    /**
     * 构造函数
     */
    YarnHelper(script, String npm) {
        super(script, npm)
    }

    /**
     * 构造函数
     */
    YarnHelper(script, String npm, String registry) {
        super(script, npm, registry)
    }
}
