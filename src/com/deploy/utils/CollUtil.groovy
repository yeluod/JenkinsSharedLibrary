package com.deploy.utils

/**
 * CollUtil
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
class CollUtil {

    /**
     * 集合是否为空
     *
     * @param collection {@link Collection}
     * @return {@link Boolean}
     */
    static def isEmpty(Collection<?> collection) {
        collection == null || collection.isEmpty()
    }

    /**
     * 集合是否不为空
     *
     * @param collection {@link Collection}
     * @return {@link Boolean}
     */
    static def isNotEmpty(Collection<?> collection) {
        !isEmpty(collection)
    }

    /**
     * Map是否为空
     *
     * @param map {@link Map}
     * @return {@link Boolean}
     */
    static def isEmpty(Map<?, ?> map) {
        null == map || map.isEmpty()
    }

    /**
     * Map是否不为空
     *
     * @param map {@link Map}
     * @return {@link Boolean}
     */
    static def isNotEmpty(Map<?, ?> map) {
        null != map && !map.isEmpty()
    }

}
