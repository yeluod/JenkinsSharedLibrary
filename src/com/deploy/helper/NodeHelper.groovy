package com.deploy.helper

import cn.hutool.core.util.StrUtil
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

    static final def INSTALL = 'install'
    static final def BUILD = 'install'

    static final def registry = '--registry'

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

    void install() {
        this.install(null)
    }

    void install(def express) {
        this.script.sh"""
            ${this.param.tool} ${INSTALL} ${express}
        """
    }

    void build() {
        this.build(null)
    }

    void build(def express) {
        this.script.sh"""
            ${this.param.tool} ${this.getNodeCommand()} ${BUILD} ${express}
        """
    }

    def getNodeCommand() {
        if (StrUtil.isNotBlank(this.param.registry)) {
            return "${registry} ${this.param.registry}"
        } else {
            return ''
        }
    }
}
