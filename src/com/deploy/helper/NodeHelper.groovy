package com.deploy.helper


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
        this.script = script
    }

    NodeHelper(script, String tool) {

        this.script = script
        this.param.tool = tool
    }

    NodeHelper(script, String tool, String registry) {

        this.script = script
        this.param.tool = tool
        this.param.registry = registry
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.param.tool} -v"
    }


}
