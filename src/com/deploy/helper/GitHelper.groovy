package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper {

    static void version(def script){
        script.sh """
            ${git} --version
        """
    }

}
