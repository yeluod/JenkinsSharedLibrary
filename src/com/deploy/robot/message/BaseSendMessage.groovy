package com.deploy.robot.message

import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONUtil

/**
 * AbstractSendMessage
 *
 * @author YeLuo
 * @since 2022/11/27
 * */
abstract class BaseSendMessage {

    static final def TIMEOUT = 3000

    abstract String getUrl()

    boolean send(String body) {
        String res = HttpUtil.post(this.getUrl(), body, TIMEOUT)
        def jsonObject = JSONUtil.parseObj(res)
        return jsonObject.getInt('errcode') == 0
    }
}
