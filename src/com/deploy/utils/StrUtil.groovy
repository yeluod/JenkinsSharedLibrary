package com.deploy.utils
/**
 * StrUtil
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
class StrUtil {

    /**
     * 字符串是否为空
     *
     * @param cs {@link CharSequence}
     * @return {@link Boolean}
     */
    static def isEmpty(CharSequence cs) {
        cs == null || cs.length() == 0
    }

    /**
     * 字符串是否不为空
     *
     * @param cs {@link CharSequence}
     * @return {@link Boolean}
     */
    static def isNotEmpty(CharSequence cs) {
        !isEmpty(cs)
    }
}
