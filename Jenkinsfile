pipeline {
    agent any   
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