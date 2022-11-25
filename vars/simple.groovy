import com.deploy.helper.DockerHelper
import com.deploy.helper.MvnHelper
import com.deploy.helper.NpmHelper
import com.deploy.helper.YarnHelper
import com.deploy.tools.Tools
import com.deploy.helper.GitHelper
import com.deploy.helper.JavaHelper

@Grab('com.google.code.gson:gson:2.10')

def call() {

    /************************************************/
    def tools = Tools.read(this)
    def gitHelper = new GitHelper(this, tools)
    def javaHelper =  new JavaHelper(this, tools)
    def mvnHelper =  new MvnHelper(this, tools)
    def npmHelper =  new NpmHelper(this, tools)
    def yarnHelper =  new YarnHelper(this, tools)
    def dockerHelper =  new DockerHelper(this, tools)
    /************************************************/

    pipeline {
        agent any

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
        }
    }
}
