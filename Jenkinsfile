pipeline {
    agent any   
    stages {
        stage('Install dependencies') {
            steps {
                echo 'Installing dependencies..'
                echo 'mvn install'

            }
        } 
        stage('Build') {
            steps {
                echo 'Building..'
                echo 'mvn package'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                echo 'mvn test'
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