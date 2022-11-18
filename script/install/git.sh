#!/bin/bash

## Git 自动安装脚本
## 基于 第三方isu源
## 查看最新Git版本 https://repo.ius.io/7/x86_64/packages/g/

GIT_VERSION='git236'
GIT_USER_NAME='jenkins'
GIT_USER_EMAIL='jenkins@company.com'
SSH_PASSWORD=''  # 空字符串表示不需要密码

uninstall() {
    echo '开始卸载旧版本 git ......'
    yum remove -y git
}

# 安装 ius-release 包 epel-release包
before_install() {
    echo '开始安装 ius-release 包 epel-release包 ......'
    yum install -y \
        https://repo.ius.io/ius-release-el7.rpm \
        https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
}

install() {
    echo '开始安装 git ......'
    yum install -y "${GIT_VERSION}"
}

after_install() {
    echo '开始配置 git ......'
    git config --global user.name "${GIT_USER_NAME}"
    git config --global user.email "${GIT_USER_EMAIL}"
    rm -rf ~/.ssh/*
    ssh-keygen -f ~/.ssh/id_rsa -t rsa -P "${SSH_PASSWORD}" -C "${GIT_USER_EMAIL}"
}

print() {
    echo -e '#########################################################################################################'
    echo -e '##  Git 安装完成 ...'
    echo -e "##  $(git --version)"
    echo -e "##  Config $(git config -l)"
    echo -e "##  id_rsa.pub  $(cat ~/.ssh/id_rsa.pub)"
    echo -e '##########################################################################################################'
}

run() {
    uninstall
    before_install
    install
    after_install
    print
}

run