@Grab('com.google.code.gson:gson:2.10')

def call(String msg) {

    pipeline {
        agent any

        stages {
            stage('Init') {
                steps {
                    sh """
                        cd ~/
                        pwd
                    """
                }
            }
        }
    }
}