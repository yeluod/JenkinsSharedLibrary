#!/bin/bash

## Maven 自动安装脚本

uninstall() {
    echo '开始卸载旧版本 maven ......'
    yum remove -y apache-maven
}

before_install() {
    echo '配置yum源 ......'
    wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo \
        -O /etc/yum.repos.d/epel-apache-maven.repo
}

install() {
    echo '开始安装 maven ......'
    yum install -y apache-maven
}

print() {
    echo -e '#########################################################################################################'
    echo -e '##  Maven 安装完成 ...'
    echo -e "##  $(mvn --version)"
    echo -e '##########################################################################################################'
}

run() {
    uninstall
    before_install
    install
    print
}

run