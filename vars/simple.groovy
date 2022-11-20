import com.deploy.tools.GlobalTool
import com.deploy.utils.GsonUtil

@Grab('com.google.code.gson:gson:2.10')

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
    GlobalTool globalTool = GsonUtil.toBean(config, GlobalTool.class)
    println(globalTool.toString())
}