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
                sh '''#!/bin/bash
                java -jar ./chatbot-backend/api-0.0.1.jar --httpPort=${server.port} &
                java -jar ./chatbot-backend/integration-0.0.1.jar --httpPort=${integration.port}&
                '''
            }
        }
    }
}
