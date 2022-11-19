package com.deploy.utils

/**
 * Assert
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
class Assert {

    /**
     * 断言为 true
     * @param expression {@link Boolean} 表达式
     * @param errorMsg {@link String} 错误信息
     */
    static void isTrue(boolean expression,String errorMsg) {
        if (!expression) {
            throw new IllegalArgumentException(errorMsg)
        }
    }

    /**
     * 断言为 false
     * @param expression {@link Boolean} 表达式
     * @param errorMsg {@link String} 错误信息
     */
    static void isFalse(boolean expression,String errorMsg) {
        if (expression) {
            throw new IllegalArgumentException(errorMsg)
        }
    }

    /**
     * 断言为空
     * @param obj {@link Object} obj
     * @param errorMsg {@link String} 错误信息
     */
    static void isEmpty(Object obj, String errorMsg) {
        if (null != obj) {
            throw new IllegalArgumentException(errorMsg)
        }
        if (obj instanceof CharSequence && StrUtil.isNotEmpty(obj as CharSequence)) {
            throw new IllegalArgumentException(errorMsg)
        }
        if (obj instanceof Map && CollUtil.isNotEmpty(obj as Map)) {
            throw new IllegalArgumentException(errorMsg)
        }
        if (obj instanceof Collection && CollUtil.isNotEmpty(obj as Collection)) {
            throw new IllegalArgumentException(errorMsg)
        }
    }

    /**
     * 断言为空
     * @param obj {@link Object} obj
     * @param errorMsg {@link String} 错误信息
     */
    static void isNotEmpty(Object obj, String errorMsg) {
        if (null == obj) {
            throw new IllegalArgumentException(errorMsg)
        }
        if (obj instanceof CharSequence && StrUtil.isEmpty(obj as CharSequence)) {
            throw new IllegalArgumentException(errorMsg)
        }
        if (obj instanceof Map && CollUtil.isEmpty(obj as Map)) {
            throw new IllegalArgumentException(errorMsg)
        }
        if (obj instanceof Collection && CollUtil.isEmpty(obj as Collection)) {
            throw new IllegalArgumentException(errorMsg)
        }
    }
}
