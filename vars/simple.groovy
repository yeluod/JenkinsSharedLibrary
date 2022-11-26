import com.deploy.helper.*

//file:noinspection GrUnresolvedAccess

@Grab('cn.hutool:hutool-all:5.8.10')
@Grab('org.yaml:snakeyaml:1.33')

def call() {

    /************************************************/
    def helper = new Helper(this, 'default')
    def gitHelper = helper.loadGitHelper()
    def javaHelper = helper.loadJavaHelper()
    def mvnHelper = helper.loadMvnHelper()
    def npmHelper = helper.loadNpmHelper()
    def yarnHelper = helper.loadYarnHelper()
    def dockerHelper = helper.loadDockerHelper()
    /************************************************/

    pipeline {
        agent any

        options {
            // 控制台输出增加时间戳
            timestamps()
            // 不允许同一个job同时执行流水线,可被用来防止同时访问共享资源等
            disableConcurrentBuilds()
            // 如果某个stage为unstable状态，则忽略后面的任务，直接退出
            skipStagesAfterUnstable()
            // 删除隐式checkout scm语句
            skipDefaultCheckout()
            // 失败重试次数
            retry(0)
            // 安静的时期 设置管道的静默时间段（以秒为单位），以覆盖全局默认值
            quietPeriod(3)
            // 超时时间 job会自动被终止
            timeout(time: 10, unit: 'MINUTES')
            // 保持构建的最大个数
            buildDiscarder(logRotator(daysToKeepStr: '7', artifactNumToKeepStr: '20'))
        }

        stages {
            stage('test') {
                steps {
                    script {
                        javaHelper.version()
                        gitHelper.version()
                        mvnHelper.version()
                        npmHelper.version()
                        yarnHelper.version()
                        dockerHelper.version()
                    }
                }
            }
            stage('CheckOut') {
                steps {
                    script {
                        gitHelper.checkOut('http://10.72.3.205:3000/sogal_it_dept/sogal-ids.git', 'dev')
                    }
                }
            }
            stage('WriteMavenSetting') {
                steps {
                    script {
                        mvnHelper.writeSettingXml()
                    }
                }
            }
            stage('MavenPackage') {
                steps {
                    script {
                        mvnHelper.packageWithAllDependencySkipTest('sogal-auth')
                    }
                }
            }
            stage('WriteDockerfile') {
                steps {
                    script {
                        Map map = [
                                "MODULE_PATH": "sogal-auth"
                        ]
                        dockerHelper.writeDockerfile(dockerHelper.param.templates.java.dockerfile, map)
                        dockerHelper.writeDockerignore(dockerHelper.param.templates.java.dockerfile)
                    }
                }
            }
            stage('DockerBuildAndPush') {
                steps {
                    script {
                        dockerHelper.build('testimages', 'dev-1.0.0')
                        dockerHelper.tag('targetimages')
                        dockerHelper.rmi()
                    }
                }
            }
        }
    }
}
