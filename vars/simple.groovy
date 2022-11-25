import com.deploy.tools.Tools
import com.deploy.helper.GitHelper
import com.deploy.helper.JavaHelper

@Grab('com.google.code.gson:gson:2.10')

def call() {

    def tools = Tools.read(this)
    def gitHelper = new GitHelper(this).init(tools)
    def javaHelper =  new JavaHelper(this).init(tools)

    pipeline {
        agent any

        stages {
            stage('Version') {
                steps {
                    script {
                        gitHelper.version()
                        javaHelper.version()
                    }
                }
            }
        }
    }
}
