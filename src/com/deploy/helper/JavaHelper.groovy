package com.deploy.helper

import com.deploy.property.Tools

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class JavaHelper extends BaseHelper {

    def script
    def java

    /**
     * 构造函数
     */
    JavaHelper(script, Tools tools) {
        this.script = script
        this.java = tools.java
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.java} --version"
    }
}
