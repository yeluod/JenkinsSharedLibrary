#!/bin/bash

## Docker 自动安装脚本

check_before_run() {
    # shellcheck disable=SC2181
    if [ $? -eq 0 ]; then
        true
    else
        echo 'docker 安装失败 ......'
        exit
    fi
}

check() {
    echo '开始检查系统信息 ......'
    NAME=$(rpm -q centos-release | awk -F "-" '{print $1}')
    VERSION=$(rpm -q centos-release | awk -F "-" '{print $3}')
    EXTENDED=$(rpm -q centos-release | awk -F "-" '{print $4}' | awk -F '.' '{print $6}')
    if [ "${NAME}" = 'centos' ] && [ "${VERSION}" = '7' ] && [ "${EXTENDED}" = 'x86_64' ]; then
        true
    else
        echo '暂不支持此系统版本'
        exit
    fi
}

uninstall() {
    echo '开始卸载旧版本 docker ......'

    # 卸载旧版本
    yum remove -y docker \
        docker-client \
        docker-client-latest \
        docker-common \
        docker-latest \
        docker-latest-logrotate \
        docker-logrotate \
        docker-engine \
        docker-ce \
        docker-ce-cli \
        docker-compose-plugin \
        docker-compose

    # 删除文件夹
    rm -rf /var/lib/docker*
}

before_install() {
    echo '安装前 ......'

    # 安装 yum-utils 工具包
    yum install -y yum-utils

    PS3='存储库 1: 官方 2:清华大学 3:阿里云, 4: 网易云  5: 中科大 请选择包索引编号: '
    # 设置 yum 存储库
    REPOS="
https://download.docker.com/linux/centos/docker-ce.repo
https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo
http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
http://mirrors.163.com/docker-ce/linux/centos/docker-ce.repo
http://mirrors.ustc.edu.cn/docker-ce/linux/centos/docker-ce.repo
"
    select REPO in $REPOS; do
        yum-config-manager --add-repo "$REPO"
        break
    done

    yum makecache fast
}

install() {
    echo '安装 ......'

    PS="是否安装最新版本 Docker (Y/n):"
    read -r -p "${PS}" REPLY
    for (( ; ; )); do
        case "$REPLY" in
        Y | y)
            yum install -y docker-ce docker-ce-cli docker-compose-plugin docker-compose
            check_before_run
            break
            ;;
        N | n)
            PS3='请选择需要安装的版本编号: '
            select VERSION in $(yum list docker-ce --showduplicates | sort -r | awk -F ' ' 'NR>4{print $2}' | awk -F ':' '{print $2}'); do
                echo "当前选择版本为: $VERSION"
                yum install -y docker-ce-"$VERSION" docker-ce-cli-"$VERSION" containerd.io docker-compose-plugin docker-compose
                check_before_run
                break
            done
            break
            ;;
        esac
    done
}

after_install() {
    echo '安装后 ......'

    mkdir -p /etc/docker
    tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://y0ovxpv6.mirror.aliyuncs.com"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "500m",
    "max-file": "3"
  }
}
EOF
    systemctl daemon-reload
    systemctl restart docker

    PS3="是否开机启动 Docker(Y/n) : "
    read -r -p "${PS3}" REPLY
    for (( ; ; )); do
        case "$REPLY" in
        Y | y)
            systemctl enable docker
            break
            ;;
        N | n)
            break
            ;;
        esac
    done
}

print() {
    echo -e '##############################################################################################################################################################################'
    echo -e '##  Docker 安装完成 ...'
    echo -e "## $(docker -v)"
    echo -e "## $(docker-compose -v)"
    echo -e "## New $(docker compose version) Doc(https://docs.docker.com/compose/compose-v2/)"
    echo -e '##############################################################################################################################################################################'
}

run() {
    check
    uninstall
    before_install
    install
    after_install
    print
}

run
