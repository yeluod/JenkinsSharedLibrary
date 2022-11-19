/**
 * simple
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
def call() {

    pipeline {
        agent any

        stages {
            stage('Hello') {
                steps {
                    echo 'Hello World'
                }
            }
        }
    }
}