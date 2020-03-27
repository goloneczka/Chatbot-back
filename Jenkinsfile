pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
         }
        stage('Copy') {
            steps {
                 sh '''#!/bin/bash
                 cp -r target/api-0.0.1.jar chatbot-backend/
                 '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''#!/bin/bash
                java -jar api-0.0.1.jar --httpPort=9090
                '''
            }
        }
    }
}
