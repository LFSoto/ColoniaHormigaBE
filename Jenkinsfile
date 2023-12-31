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
        stage('Build') {
            steps {
                echo 'Building Reina application...'
                sh 'mvn clean install compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }
        stage('Sonar Scan') {
          steps {
              echo 'Running Sonar Scanner...'
              withSonarQubeEnv(installationName: 'SonarQube') { 
              sh "mvn clean package sonar:sonar -Dsonar.projectKey=SubSistemaReina -Dsonar.projectName='SubSistemaReina'"
            }
          }
        }
        stage("Quality Gate") {
          steps {
              echo 'Quality Gate check...'
              timeout(time: 2, unit: 'MINUTES') {
              waitForQualityGate abortPipeline: true
            }
          }
        } 
        stage("Package executable") {
          steps {
              echo 'Creating java executable file...'
              sh 'mvn package -Dmaven.test.skip'
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

