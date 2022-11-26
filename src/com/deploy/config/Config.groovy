package com.deploy.config

import org.yaml.snakeyaml.Yaml

/**
 * Config
 *
 * @author YeLuo
 * @since 2022/11/26
 * */
@SuppressWarnings('unused')
class Config {

    Git git

    Java java

    Mvn mvn

    Npm npm

    Yarn yarn

    Docker docker

    KubeCtl kubectl

    static class Git {

        String tool

        String credential
    }


    static class Java {

        String tool
    }


    static class Mvn {

        String tool

        Setting setting


        static class Setting {

            String source
        }
    }


    static class Npm {

        String tool

        String registry
    }


    static class Yarn {

        String tool

        String registry
    }


    static class Docker {

        String tool

        Registry registry

        Templates templates


        static class Registry {

            String host

            String credential
        }


        static class Templates {

            Java java

            Node node


            static class Java {

                String dockerignore

                String dockerfile

                String kubernetes
            }


            static class Node {

                String nginx

                String dockerignore

                String dockerfile

                String kubernetes
            }
        }
    }


    static class KubeCtl {

        String tool
    }

    static def load(script, env) {
        String yamlSource = script.libraryResource("${env}/config.yml")
        new Yaml().loadAs(yamlSource, Config)
    }

    @Override
    String toString() {
        return "Config{" +
                "git=" + git +
                ", java=" + java +
                ", mvn=" + mvn +
                ", npm=" + npm +
                ", yarn=" + yarn +
                ", docker=" + docker +
                ", kubectl=" + kubectl +
                '}';
    }

}
