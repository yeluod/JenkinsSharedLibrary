package com.deploy.config

import com.deploy.helper.param.*

/**
 * Config
 *
 * @author YeLuo
 * @since 2022/11/26
 * */
@SuppressWarnings('unused')
class Config {

    JavaToolParam javaToolParam

    GitToolParam gitToolParam

    MvnToolParam mvnToolParam

    NodeToolParam npmToolParam

    NodeToolParam yarnToolParam

    DockerToolParam dockerToolParam

    KubectlToolParam kubectlToolParam

}
