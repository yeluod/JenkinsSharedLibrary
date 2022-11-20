import com.deploy.helper.GitHelper

/**
 * simple
 *
 * @author YeLuo
 * @since 2022/11/19
 * */
def call(String msg) {

    def gitHelper = new GitHelper(this, '/usr/bin/git')
    println('-------------------')
    gitHelper.version()

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