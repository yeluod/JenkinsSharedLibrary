import com.deploy.helper.DockerHelper
import com.deploy.helper.MvnHelper
import com.deploy.helper.NpmHelper
import com.deploy.helper.YarnHelper
import com.deploy.property.Credentials
import com.deploy.property.Tools
import com.deploy.helper.GitHelper
import com.deploy.helper.JavaHelper

@Grab('com.google.code.gson:gson:2.10')

def call() {

    /************************************************/
    def tools = Tools.read(this)
    def credentials = Credentials.read(this)
    def gitHelper = new GitHelper(this, tools, credentials)
    def javaHelper =  new JavaHelper(this, tools)
    def mvnHelper =  new MvnHelper(this, tools)
    def npmHelper =  new NpmHelper(this, tools)
    def yarnHelper =  new YarnHelper(this, tools)
    def dockerHelper =  new DockerHelper(this, tools)
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
            stage('Version') {
                steps {
                    script {
                        gitHelper.version()
                        javaHelper.version()
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
                        gitHelper.checkOut("http://10.72.3.205:3000/sogal_it_dept/sogal-ids.git", "null/dev/dev")
                    }
                }
            }
            stage('WriteSettingXml') {
                steps {
                    script {
                        mvnHelper.writeSettingXml()
                    }
                }
            }
            stage('MavenPackage') {
                steps {
                    script {
                        println('skip')
                        // mvnHelper.packageWithAllDependency('sogal-auth')
                    }
                }
            }
            stage('DockerBuildAndPush') {
                steps {
                    script {
                        Map map = [
                                'MODULE_PATH': 'sogal-auth'
                        ]
                        dockerHelper.writeDockerfile('templates/Springboot/Dockerfile', map)
                        dockerHelper.writeDockerignore('templates/Springboot/.dockerignore')
                        dockerHelper.build('testimage', 'latest')
                    }
                }
            }
        }
    }
}
