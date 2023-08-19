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
                sh 'java --version'
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
        stage("Create package") {
          steps {
              echo 'Creating java executable file...'
              sh 'mvn package -Dmaven.test.skip'
            }
        }
        stage("Deploy application") {
          steps {
              echo 'Deploying application...'
              sh 'java -jar ./target/reina-0.0.1-SNAPSHOT.jar'              
            }
        }
    }
}

