import com.deploy.tools.GlobalTool
import com.deploy.utils.GsonUtil

@Grab('com.google.code.gson:gson:2.10')

def call(String msg) {

    pipeline {
        agent any

        stages {
            stage('Init') {
                steps {
                    script {

                    }
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
    println(globalTool.git)
    println(globalTool.java)
}