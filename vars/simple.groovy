import com.deploy.helper.GitHelper

@Grab('com.google.code.gson:gson:2.10')

def call() {

    def helper = new GitHelper(this)

    pipeline {
        agent any

        stages {
            stage('Init') {
                steps {
                    script {
                        println helper
                    }
                }
            }
        }
    }
}
