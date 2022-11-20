package com.deploy.utils

/**
 * FileUtil
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class FileUtil {

    /**
     * 判断文件是否存在
     *
     * @param path {@link String} 文件路径
     * @return {@link Boolean} 存在:true; 不存在:false
     */
    static def exist(String path) {
        null StrUtil.isNotEmpty(path) && new File(path).exists();
    }

}
