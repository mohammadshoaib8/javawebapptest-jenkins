pipeline {
    agent any
    tools {
        maven 'maven3'
    }
    stages {
        stage ('code cloning'){
            steps {
                git credentialsId: '5344fe9e-333a-4e01-844b-fc56f14330fd', url: 'https://github.com/mohammadshoaib8/javawebapptest-jenkins.git'
            }
        }
        stage ('build'){
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('deploy to continer'){
            steps {
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat', path: '', url: 'http://13.229.139.142:9090/')], contextPath: 'firstpipelinejob', war: '**/*.war'
            }
        }
    }
}
