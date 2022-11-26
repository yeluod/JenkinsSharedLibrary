package com.deploy.helper

import cn.hutool.core.util.StrUtil
import com.deploy.config.Config
import com.deploy.property.Tools

/**
 * MvnHelper
 *
 * @author YeLuo
 * @since 2022/11/22
 * */
@SuppressWarnings('unused')
class MvnHelper extends BaseHelper {

    def script
    def mvn
    def outputSettingXmlPath

    static def SETTING_XML_NAME = 'setting.xml'

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
    static def s = "-s"

    /**
     * 构造函数
     */
    MvnHelper(script) {
        this.script = script
    }

    /**
     * 构造函数
     */
    MvnHelper(script, Config config) {
        this.script = script
        this.mvn = config.mvn.tool
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
     *
     * @param source {@link String} 资源文件
     */
    void writeSettingXml(String source) {
        this.writeSettingXml(source, StrUtil.DOT)
    }

    /**
     * 写入 maven setting文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     */
    void writeSettingXml(String source, String outputPath) {
        this.writeSettingXml(source, outputPath, SETTING_XML_NAME)
    }

    /**
     * 写入 maven setting文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @param outputName {@link String} 输出名称
     */
    void writeSettingXml(String source, String outputPath, String outputName) {
        this.script.println '开始写入 maven setting.xml ...'
        def xmlSource = this.script.libraryResource(source)
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee(outputPath, outputName, xmlSource)
        // 赋值 setting.xml 文件路径
        this.outputSettingXmlPath = outputPath + StrUtil.SLASH + outputName
    }

    /**
     * 打包 (所有依赖全部打进来)
     *
     * @param module {@link String} 模块 groupId OR path
     */
    void packageWithAllDependencySkipTest(String module) {
        this.script.sh """
            ${this.mvn} ${CLEAN} ${PACKAGE} ${s} ${this.outputSettingXmlPath} ${pl} ${module} ${SKIP_TEST} ${am}
        """
    }

    /**
     * 打包到本地仓库 (所有依赖全部打进来)
     *
     * @param module {@link String} 模块 groupId OR path
     */
    void installWithAllDependencySkipTest(String module) {
        this.script.sh """
            ${this.mvn} ${CLEAN} ${INSTALL} ${s} ${this.outputSettingXmlPath} ${pl} ${module} ${SKIP_TEST} ${am}
        """
    }

    /**
     * 发布到远程仓库 (所有依赖全部打进来)
     *
     * @param module {@link String} 模块 groupId OR path
     */
    void deployWithAllDependencySkipTest(String module) {
        this.script.sh """
            ${this.mvn} ${CLEAN} ${DEPLOY} ${s} ${this.outputSettingXmlPath} ${pl} ${module} ${SKIP_TEST} ${am}
        """
    }

}
