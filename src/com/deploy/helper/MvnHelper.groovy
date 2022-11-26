package com.deploy.helper


import cn.hutool.core.util.StrUtil
import com.deploy.helper.param.MvnToolParam

/**
 * MvnHelper
 *
 * @author YeLuo
 * @since 2022/11/22
 * */
@SuppressWarnings('unused')
class MvnHelper extends BaseHelper {

    def script
    MvnToolParam param

    String outputSettingXmlPath

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
    MvnHelper(script, String mvn, String settingSource) {
        this.script = script
        this.param.mvn = mvn
        this.param.setting.source = settingSource
    }

    /**
     * 输出版本号
     */
    @Override
    void version() {
        this.script.sh "${this.param.mvn} -v"
    }

    /**
     * 写入 maven setting文件
     */
    void writeSettingXml() {
        this.writeSettingXml(this.param.setting.source, StrUtil.DOT)
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
            ${this.param.mvn} ${CLEAN} ${PACKAGE} ${this.getMavenCommand(module)}
        """
    }

    /**
     * 打包到本地仓库 (所有依赖全部打进来)
     *
     * @param module {@link String} 模块 groupId OR path
     */
    void installWithAllDependencySkipTest(String module) {
        this.script.sh """
            ${this.param.mvn} ${CLEAN} ${INSTALL} ${this.getMavenCommand(module)}
        """
    }

    /**
     * 发布到远程仓库 (所有依赖全部打进来)
     *
     * @param module {@link String} 模块 groupId OR path
     */
    void deployWithAllDependencySkipTest(String module) {
        this.script.sh """
            ${this.param.mvn} ${CLEAN} ${DEPLOY} ${this.getMavenCommand(module)}
        """
    }

    /**
     * 组装 maven 命令
     */
    def getMavenCommand(String module) {
        String command = "${pl} ${module} ${SKIP_TEST} ${am}"
        if (StrUtil.isNotBlank(this.outputSettingXmlPath)) {
            command = "${command} ${s} ${this.outputSettingXmlPath}"
        }
        command
    }

}
