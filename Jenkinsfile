pipeline {
    agent any
    environment {
        PATH="/opt/apache-maven-3.9.4/bin:$PATH"
    }   
    stages {
        stage('Install dependencies') {
            steps {
                echo 'Installing dependencies..'
                sh 'mvn clean install'

            }
        } 
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn package'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                echo 'mvn deploy'
            }
        }
    }
}