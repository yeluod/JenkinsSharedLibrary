import com.deploy.helper.DockerHelper
import com.deploy.helper.MvnHelper
import com.deploy.helper.NpmHelper
import com.deploy.helper.YarnHelper
import com.deploy.property.Credentials
import com.deploy.property.Tools
import com.deploy.helper.GitHelper
import com.deploy.helper.JavaHelper

@Grab('com.google.code.gson:gson:2.10')

def call() {

    /************************************************/
    def tools = Tools.read(this)
    def credentials = Credentials.read(this)
    def gitHelper = new GitHelper(this, tools, credentials)
    def javaHelper =  new JavaHelper(this, tools)
    def mvnHelper =  new MvnHelper(this, tools)
    def npmHelper =  new NpmHelper(this, tools)
    def yarnHelper =  new YarnHelper(this, tools)
    def dockerHelper =  new DockerHelper(this, tools)
    /************************************************/

    pipeline {
        agent any

        stages {
            stage('Version') {
                steps {
                    script {
                        gitHelper.version()
                        javaHelper.version()
                        mvnHelper.version()
                        npmHelper.version()
                        yarnHelper.version()
                        dockerHelper.version()
                    }
                }
            }
            stage('CheckOut') {
                steps {
                    script {
                        gitHelper.checkOut("http://10.72.3.205:3000/sogal_it_dept/sogal-ids.git", "null/dev/dev")
                    }
                }
            }
            stage('WriteSettingXml') {
                steps {
                    script {
                        mvnHelper.writeSettingXml()
                    }
                }
            }
        }
    }
}
