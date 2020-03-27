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
                 cp -r ./api/target/api-0.0.1.jar ./chatbot-backend/
                 cp -r ./integration/target/integration-0.0.1.jar ./chatbot-backend/
                 '''
            }
        }

        stage('Deploy') {
            steps {
                readProp = readProperties file: 'application-prod.yml'
                sh '''#!/bin/bash
                java -jar ./chatbot-backend/api-0.0.1.jar --httpPort=8080 &
                java -jar ./chatbot-backend/integration-0.0.1.jar --httpPort=9090 &
                '''
            }
        }
    }
}
