package com.deploy.robot

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Markdown {

    /**
     * 环境
     */
    String environment
    /**
     * 模块
     */
    String module
    /**
     * 分支
     */
    String branch
    /**
     * 耗时
     */
    String time
    /**
     * 描述
     */
    String description
    /**
     * 构建URL
     */
    String buildUrl

    def environment(String environment) {
        this.environment = environment
        this
    }

    def module(String module) {
        this.module = module
        this
    }

    def branch(String branch) {
        this.branch = branch
        this
    }

    def time(String time) {
        this.time = time
        this
    }

    def description(String description) {
        this.description = description
        this
    }

    def buildUrl(String buildUrl) {
        this.buildUrl = buildUrl
        this
    }

    def build() {
        return "##### <font color=#0080FF>构建通知: </font>\n" +
                "> 时间：${LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm:ss'))}\n" +
                "> 环境：${this.environment}\n" +
                "> 分支：${this.branch}\n" +
                "> 模块：${this.module}\n" +
                "> 耗时：${this.time}\n" +
                "> 描述：${this.description}\n" +
                "> BUILD_URL：[跳转](${this.buildUrl})"
    }
}
