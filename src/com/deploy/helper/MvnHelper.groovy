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

    static def SETTING_XML = 'setting.xml'

    static def CLEAN = 'clean'
    static def TEST = 'test'
    static def PACKAGE = 'package'
    static def INSTALL = 'install'
    static def DEPLOY = 'deploy'

    static def SKIP_TEST = '-Dmaven.test.skip=true'

    /**
     * groupId OR path
     */
    static def pl = '-pl'
    /**
     * am amd 必须配合 pl 一起使用否则不生效
     */
    static def am = '-am'
    static def amd = '-amd'
    static def s = "-s ${SETTING_XML}"

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

    /**
     * 写入 maven setting文件
     */
    void writeSettingXml() {
        this.script.println '开始写入 maven setting.xml ...'
        def xml = this.script.libraryResource('conf/maven/setting.xml')
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee('.', "${SETTING_XML}", xml)
    }

    /**
     * 打包 (所有依赖全部打进来)
     *
     * @param module {@link String} 模块 groupId OR path
     */
    void packageWithAllDependency(String module) {
        this.script.sh """
            ${this.mvn} ${CLEAN} ${PACKAGE} ${s} ${pl} ${module} ${SKIP_TEST} ${am}
        """
    }


}
