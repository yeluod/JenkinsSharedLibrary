/**
 * simple
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
def call(String msg) {

    readTools()

    pipeline {
        agent any

        parameters {
            choice(
                    name: 'GitBranches',
                    description: 'Git分支',
                    choices: ['main', 'test', 'dev']
            )
        }

        stages {
            stage('Hello') {
                steps {
                    echo "${msg}"
                }
            }
        }
    }
}

/**
 * readTools
 */
def readTools() {
    String config = libraryResource('global/config.json')
    println(config)
}