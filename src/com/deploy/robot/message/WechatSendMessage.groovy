package com.deploy.robot.message

import cn.hutool.json.JSONUtil
import com.deploy.robot.Markdown

/**
 * WechatSendMessage
 *
 * @author YeLuo
 * @since 2022/11/27
 * */
class WechatSendMessage extends BaseSendMessage {

    String key

    static final def URL = 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send'

    WechatSendMessage(String key) {
        this.key = key
    }

    @Override
    String getUrl() {
        return URL + '?key=' + this.key
    }

    static def markdownMessage(String content) {
        Map<String, Object> map = [
                "msgtype" : "markdown",
                "markdown": [
                        // "mentioned_list": Collections.singleton("@all"),
                        "content": content
                ]
        ]
        return JSONUtil.toJsonStr(map)
    }

    static def textMessage(String content) {
        Map<String, Object> map = [
                "msgtype": "text",
                "text"   : [
                        //"mentioned_list": Collections.singleton("@all"),
                        "content": content
                ]
        ]
        return JSONUtil.toJsonStr(map)
    }
}
