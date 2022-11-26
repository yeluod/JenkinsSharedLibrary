package com.deploy.helper

import cn.hutool.core.lang.Assert
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
        Assert.notNull(script, '当前脚本不能为空')
        this.script = script
    }

    /**
     * 构造函数
     */
    JavaHelper(script, String java) {
        Assert.notNull(script, '当前脚本不能为空')
        Assert.notBlank(java, 'Java 可执行文件配置不能为空')
        this.script = script
        this.param.java = java
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.checkParam()
        this.script.sh "${this.param.java} --version"
    }

    void checkParam() {
        Assert.notNull(this.script, '当前脚本不能为空')
        Assert.notBlank(this.param.java, 'Java 可执行文件配置不能为空')
    }
}
