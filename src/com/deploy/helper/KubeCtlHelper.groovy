package com.deploy.helper

import cn.hutool.core.util.StrUtil
import com.deploy.helper.param.KubectlToolParam

/**
 * KubeCtlHelper
 *
 * @author YeLuo
 * @since 2022/11/27
 * */
class KubeCtlHelper extends BaseHelper {

    def script
    KubectlToolParam param

    String yamlPath

    static def YAML_NAME = 'app.yml'

    static def CREATE = 'create'
    static def APPLY = 'apply'

    static def f = '-f'

    KubeCtlHelper(script) {
        this.script = script
    }

    @Override
    void version() {
        this.script.sh "${this.param.kubectl} version"
    }

    /**
     * 写入 k8s yaml 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @return {@link String} 输出文件路径
     */
    void writeYamlFile(String source, Map map) {
        this.writeYamlFile(source, StrUtil.DOT, YAML_NAME, map)
    }

    /**
     * 写入 k8s yaml 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @return {@link String} 输出文件路径
     */
    void writeYamlFile(String source, String outputPath) {
        this.writeYamlFile(source, outputPath, YAML_NAME)
    }

    /**
     * 写入 k8s yaml 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @param outputName {@link String} 输出名称
     * @return {@link String} 输出文件路径
     */
    void writeYamlFile(String source, String outputPath, String outputName) {
        this.script.println '开始写入 k8s yaml ...'
        // 赋值  k8s yaml  文件路径
        this.yamlPath = new WriteHelper(this.script).writeFile(source, outputPath, outputName, null)
    }

    /**
     * 写入 k8s yaml 文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @param outputName {@link String} 输出名称
     * @param variable {@link Map} 需要替换的变量
     * @return {@link String} 输出文件路径
     */
    void writeYamlFile(String source, String outputPath, String outputName, Map variable) {
        this.script.println '开始写入 k8s yaml ...'
        // 赋值  k8s yaml  文件路径
        this.yamlPath = new WriteHelper(this.script).writeFile(source, outputPath, outputName, variable)
    }

    /**
     * 基于文件名或标准输入，将新的配置应用到资源上
     */
    void create() {
        this.script.sh """
            ${this.param.kubectl} ${CREATE} ${f} ${this.yamlPath}
        """
    }

    /**
     * 基于文件名或标准输入，将新的配置应用到资源上
     */
    void apply() {
        this.script.sh """
            ${this.param.kubectl} ${APPLY} ${f} ${this.yamlPath}
        """
    }
}
