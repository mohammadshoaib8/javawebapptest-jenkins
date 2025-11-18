pipeline {
    agent any
    tools {
        maven 'maven3'
    }
    environment {
        IMAGE_NAME = 'msshoaib2255457/javajenkinstest'
        IMAGE_TAG = 'v${BUILD_NUMBER}'
        AWS_REGION = 'ap-southeast-1'
        CLUSTER_NAME = 'karpenter-25-blueprints'
        HELM_CHART = './helm-chart'
        HELM_RELEASE = 'mywebapp'
    }
    stages {
        stage ('Git Checkout') {
            steps {
                echo "Cloning the latest code from github branch"
                git branch: 'master', url: 'https://github.com/mohammadshoaib8/javawebapptest-jenkins.git'
            }
        }
        stage ('Code Build') {
            steps {
                echo "Compiling the project code"
                sh 'mvn clean package -Dskiptests'
            }
        }
        stage ('Exicute unit tests') {
            steps {
                echo "Exicuting the unit tests of project code"
                sh "mvn test"
            }
        }
        stage ('Code Quality Check - Sonarqube') {
            steps {
                echo "Checking code quality and bugs"
                withSonarQubeEnv('sonarqube-server') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=javawebapptest"
                }
            }
        }
        stage ('Build Docker Image') {
            steps {
                echo "building the docker image"
                sh """
                    docker build -t $IMAGE_NAME:$IMAGE_TAG .
                    docker tag $IMAGE_NAME:$IMAGE_TAG $IMAGE_NAME:latest
                """
            }
        }
        stage ('Docker Image Push') {
            steps {
                echo "Docker image push to docker hub"
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://hub.docker.com/repository/docker/msshoaib2255457/javajenkinstest') {
                sh """
                    docker push $IMAGE_NAME:latest
                """
                }
            }
        }
    }
    
}
