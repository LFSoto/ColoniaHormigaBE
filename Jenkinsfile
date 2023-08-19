pipeline {
    agent any
    options {
        disableConcurrentBuilds abortPrevious: true
    }
    tools {
        maven "mvn"
    }
    environment {
        PATH = "$PATH:/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation"
    }
    stages {
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Sonar Scan') {
          steps {
              echo 'Running Sonar Scanner...'
              withSonarQubeEnv(installationName: 'SonarQube') { 
              sh "mvn clean compile sonar:sonar -Dsonar.projectKey=SubSistemaReina -Dsonar.projectName='SubSistemaReina'"
            }
          }
        }
        stage("Quality Gate") {
          steps {
              echo 'Evaluation Quality Gate...'
              timeout(time: 2, unit: 'MINUTES') {
              waitForQualityGate abortPipeline: true
            }
          }
        } 
        stage("Deploy") {
          steps {
              echo 'Deploying application...'
              sh 'mvn spring-boot:run'
            }
        }
    }
}

