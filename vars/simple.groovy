/**
 * simple
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
def call(String msg) {

    pipeline {
        agent any

        stages {
            stage('Hello') {
                steps {
                    echo "${msg}"
                }
            }
        }
    }
}