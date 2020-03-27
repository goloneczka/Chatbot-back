pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                    sh '''#!/bin/bash
                    mvn clean compile
                    '''
            }
        }
        stage('Test') {
            steps {
                     sh '''#!/bin/bash
                     mvn test
                     '''
            }
        }
        stage('Deploy') {
            steps {
                   sh '''#!/bin/bash
                   mvn deploy
                   '''
            }
        }
    }
}
