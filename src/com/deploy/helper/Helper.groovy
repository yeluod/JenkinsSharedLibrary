package com.deploy.helper


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
            println yamlSource
            this.config = new Yaml().loadAs(yamlSource, Config.class)

        }
    }

    GitHelper loadGitHelper() {
        this.loadConfig()
        def helper = new GitHelper(this.script)
        helper.param = this.config.gitToolParam
        return helper
    }

    JavaHelper loadJavaHelper() {
        this.loadConfig()
        def helper = new JavaHelper(this.script)
        helper.param = this.config.javaToolParam
        return helper
    }

    MvnHelper loadMvnHelper() {
        this.loadConfig()
        def helper = new MvnHelper(this.script)
        helper.param = this.config.mvnToolParam
        return helper
    }

    NpmHelper loadNpmHelper() {
        this.loadConfig()
        def helper = new NpmHelper(this.script)
        helper.param = this.config.npmToolParam
        return helper
    }

    YarnHelper loadYarnHelper() {
        this.loadConfig()
        def helper = new YarnHelper(this.script)
        helper.param = this.config.yarnToolParam
        return helper
    }

}
