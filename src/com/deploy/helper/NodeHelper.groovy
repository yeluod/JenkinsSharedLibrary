package com.deploy.helper

import cn.hutool.core.lang.Assert
import com.deploy.helper.param.NodeToolParam

/**
 * NodeHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
abstract class NodeHelper extends BaseHelper {

    protected def script
    protected NodeToolParam param

    NodeHelper(script) {
        Assert.notNull(script, '当前脚本不能为空')
        this.script = script
    }

    NodeHelper(script, String tool) {
        Assert.notNull(script, '当前脚本不能为空')
        Assert.notBlank(tool, 'Node 可执行文件配置不能为空')
        this.script = script
        this.param.tool = tool
    }

    NodeHelper(script, String tool, String registry) {
        Assert.notNull(script, '当前脚本不能为空')
        Assert.notBlank(tool, 'Node 可执行文件配置不能为空')
        this.script = script
        this.param.tool = tool
        this.param.registry = registry
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.checkParam()
        this.script.sh "${this.param.tool} -v"
    }

    void checkParam() {
        Assert.notNull(this.script, '当前脚本不能为空')
        Assert.notBlank(this.param.tool, 'Node 可执行文件配置不能为空')
    }


}
