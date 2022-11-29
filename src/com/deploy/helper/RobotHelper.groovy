package com.deploy.helper

import com.deploy.helper.param.RobotToolParam
import com.deploy.robot.Markdown
import com.deploy.robot.message.WechatSendMessage

/**
 * RobotHelper
 *
 * @author YeLuo
 * @since 2022/11/28
 * */
class RobotHelper {

    def script

    RobotToolParam param

    RobotHelper(script) {
        this.script = script
    }

    void sendWechatMessage(environment, branch, description) {
        def body = new Markdown()
                .environment("${environment}")
                .module("${this.script.env.JOB_NAME}")
                .branch("${branch}")
                .time("${this.script.currentBuild.duration} ms")
                .description("${description}")
                .buildUrl("${this.script.env.BUILD_URL}/console")
                .build()
        new WechatSendMessage(this.param.wechatKey).send(WechatSendMessage.markdownMessage(body))
    }

}
