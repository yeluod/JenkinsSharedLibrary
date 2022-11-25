package com.deploy.helper

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
    void cat(path, fileName, body) {
        this.script.sh """
            cat ${path}/${fileName} <<-'EOF'
${body}
EOF
        """
    }

    /**
     * 写入文件
     */
    void tee(path, fileName, body) {
        this.script.sh """
            tee ${path}/${fileName} <<-'EOF'
${body}
EOF
        """
    }
}
