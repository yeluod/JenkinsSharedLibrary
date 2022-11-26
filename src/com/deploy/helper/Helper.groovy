package com.deploy.helper

import cn.hutool.json.JSONUtil
import com.deploy.config.Config
import org.yaml.snakeyaml.Yaml

/**
 * Helper
 *
 * @author YeLuo
 * @since 2022/11/26
 * */
class Helper {

    def script
    def env
    Config config

    Helper(script, env) {
        this.script = script
        this.env = env
    }

    def loadConfig() {
        if (this.config == null) {
            String yamlSource = this.script.libraryResource("${this.env}/config.yml")
            println(JSONUtil.toJsonPrettyStr(yamlSource))
            this.config = new Yaml().loadAs(yamlSource, Config.class)
        }
    }

    def loadJavaHelper() {
        this.loadConfig()
        def helper = new JavaHelper(this.script)
        helper.param = this.config.javaToolParam
        return helper
    }

    def loadGitHelper() {
        this.loadConfig()
        def helper = new GitHelper(this.script)
        helper.param = this.config.gitToolParam
        return helper
    }

}
