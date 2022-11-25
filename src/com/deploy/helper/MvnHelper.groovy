package com.deploy.helper

import com.deploy.property.Tools

/**
 * MvnHelper
 *
 * @author YeLuo
 * @since 2022/11/22
 * */
class MvnHelper extends BaseHelper {

    def script
    def mvn

    /**
     * 构造函数
     */
    MvnHelper(script, Tools tools) {
        this.script = script
        this.mvn = tools.mvn
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.mvn} -v"
    }

    void writeSettingXml() {
        this.script.println '开始写入 maven setting.xml ...'
        def xml = this.script.libraryResource('conf/maven/setting.xml')
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee('.', 'setting.xml', xml)
    }


}
