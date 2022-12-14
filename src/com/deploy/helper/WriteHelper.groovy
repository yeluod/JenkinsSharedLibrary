package com.deploy.helper

import cn.hutool.core.util.StrUtil

/**
 * WriteHelper
 *
 * @author YeLuo
 * @since 2022/11/25
 * */
class WriteHelper {

    def script

    /**
     * 构造函数
     */
    WriteHelper(script) {
        this.script = script
    }

    /**
     * 写入文件
     */
    void cat(def path, def fileName, def body) {
        this.script.sh """
            cat ${path}/${fileName} <<-'EOF'
${body}
EOF
        """
    }

    /**
     * 写入文件, 替换变量
     * @return
     */
    def cat(def path, def fileName, def body, Map map) {
        this.cat(path, fileName, replaceVariable(body, map))
    }

    /**
     * 写入文件
     */
    void tee(def path, def fileName, def body) {
        this.script.sh """
            tee ${path}/${fileName} <<-'EOF'
${body}
EOF
        """
    }

    /**
     * 写入文件, 替换变量
     */
    void tee(def path, def fileName, def body, Map map) {
        this.tee(path, fileName, replaceVariable(body, map))
    }

    /**
     * 替换变量
     */
    static def replaceVariable(String body, Map map) {
        if (map != null) {
            for (final def entry in map.entrySet()) {
                //noinspection GroovyAssignabilityCheck
                body = StrUtil.replace(body, entry.getKey(), entry.getValue())
            }
        }
        body
    }

    /**
     * 写入文件
     *
     * @param source {@link String} 资源文件
     * @param outputPath {@link String} 输出位置
     * @param outputName {@link String} 输出名称
     * @param variable {@link Map} 需要替换的变量
     * @return {@link String} 输出文件路径
     */
    String writeFile(String source, String outputPath, String outputName, Map variable) {
        def dockerfileSource = this.script.libraryResource(source)
        def writeHelper = new WriteHelper(this.script)
        writeHelper.tee(outputPath, outputName, dockerfileSource, variable)
        return outputPath + StrUtil.SLASH + outputName
    }
}
