package com.deploy.helper.param

/**
 * DockerToolParam
 *
 * @author YeLuo
 * @since 2022/11/26
 * */
class DockerToolParam {

    String docker
    Registry registry
    Templates templates

    static class Registry {

        String host

        String credential
    }

    static class Templates {

        FileParam java
        FileParam node

    }

    static class FileParam {

        String nginx
        String dockerignore
        String dockerfile
        String kubernetes

    }

}
