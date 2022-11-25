package com.deploy.helper

/**
 * GitHelper
 *
 * @author YeLuo
 * @since 2022/11/20
 * */
class GitHelper extends BaseHelper{

    def git

    def version(){
        sh """
            /use/bin/git --version
        """
    }

}
