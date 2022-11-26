package com.deploy.helper


import com.deploy.helper.param.JavaToolParam

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
@SuppressWarnings('unused')
class JavaHelper extends BaseHelper {

    def script
    JavaToolParam param

    /**
     * 构造函数
     */
    JavaHelper(script) {
        this.script = script
    }

    /**
     * 构造函数
     */
    JavaHelper(script, String java) {
        this.script = script
        this.param.java = java
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.param.java} --version"
    }

}
