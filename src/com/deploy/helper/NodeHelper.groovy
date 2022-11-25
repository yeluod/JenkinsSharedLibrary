package com.deploy.helper

/**
 * NodeHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
abstract class NodeHelper extends BaseHelper {

    protected def script
    protected def tool

    @Override
    void version() {
        this.script.sh "${this.tool} -v"
    }


}
