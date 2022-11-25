import com.deploy.helper.GitHelper
import com.deploy.tools.Tools

@Grab('com.google.code.gson:gson:2.10')

def call() {

    def tools = Tools.read(this)
    def helper = new GitHelper(this).init(tools)

    pipeline {
        agent any

        stages {
            stage('Init') {
                steps {
                    script {
                        println helper
                        helper.version()
                    }
                }
            }
        }
    }
}
