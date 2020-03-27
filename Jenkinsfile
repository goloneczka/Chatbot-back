pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                withMaven(maven: 'maven_3_6_3'){
                    sh '''#!/bin/bash
                    mvn clean compile
                    '''
                }
            }
        }
        stage('Test') {
            steps {
                 withMaven(maven: 'maven_3_6_3'){
                     sh '''#!/bin/bash
                     mvn test
                     '''
                 }
            }
        }
        stage('Deploy') {
            steps {
                withMaven(maven: 'maven_3_6_3'){
                   sh '''#!/bin/bash
                   mvn deploy
                   '''
                 }
            }
        }
    }
}
