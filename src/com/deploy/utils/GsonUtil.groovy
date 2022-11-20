package com.deploy.utils

import com.google.gson.Gson

/**
 * GsonUtil
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GsonUtil {

    private static final Gson GSON = new Gson()

    static <T> T toBean(String jsonStr, Class<T> tClass) {
        try {
            return GSON.fromJson(jsonStr, tClass);
        } catch (Exception e) {
            e.printStackTrace()
            // ...
        }
        return null;
    }

}
