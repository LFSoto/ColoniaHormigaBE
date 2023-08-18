pipeline {
    agent any
    tools {
        maven "mvn"
    }
    environment {
        PATH = "$PATH:/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation"
    }
    stages {
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Scan') {
          steps {
            withSonarQubeEnv(installationName: 'SonarQube') { 
                sh "mvn clean sonar:sonar -Dsonar.projectKey=SubSistemaReina -Dsonar.projectName='SubSistemaReina'"
            }
          }
        }
        stage("Quality Gate") {
          steps {
            timeout(time: 2, unit: 'MINUTES') {
              waitForQualityGate abortPipeline: true
            }
          }
        }       
    }
}
