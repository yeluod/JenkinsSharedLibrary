import com.deploy.helper.GitHelper

@Grab('com.google.code.gson:gson:2.10')

def call(String msg) {

    pipeline {
        agent any

        stages {
            stage('Init') {
                steps {
                    script {
                        def helper = new GitHelper(this)
                        println helper
                    }
                }
            }
        }
    }
}
