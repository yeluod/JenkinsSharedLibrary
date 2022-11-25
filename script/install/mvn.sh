#!/bin/bash

## Maven 自动安装脚本
TAR_URL='https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz'
WORK_PATH='/work/tool/'
TAR_NAME='apache-maven-3.8.6-bin.tar.gz'

downloadBin() {
    echo '开始下载maven二进制文件 ......'
    wget "${TAR_URL}" -O "${TAR_NAME}"
}

mvTar() {
    mv "${TAR_NAME}" "${WORK_PATH}"
}

extract() {
    echo '解压 ......'
    tar -tar xzvf "${TAR_NAME}"
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
