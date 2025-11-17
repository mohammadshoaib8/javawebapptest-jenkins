pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    environment {
        IMAGE_NAME   = "msshoaib2255457/javajenkinstest"
        IMAGE_TAG    = "v${BUILD_NUMBER}"
        AWS_REGION   = "ap-southeast-1"
        CLUSTER_NAME = "karpenter-25-blueprints"
        HELM_RELEASE = "mywebapp"
        HELM_CHART   = "./helm-chart"
    }

    stages {

        stage('Code Cloning') {
            steps {
                git credentialsId: '5344fe9e-333a-4e01-844b-fc56f14330fd', url: 'https://github.com/mohammadshoaib8/javawebapptest-jenkins.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                    docker build -t $IMAGE_NAME:$IMAGE_TAG .
                    docker tag $IMAGE_NAME:$IMAGE_TAG $IMAGE_NAME:latest
                """
            }
        }

        stage('Docker Image Push') {
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh """
                        docker push $IMAGE_NAME:$IMAGE_TAG
                        docker push $IMAGE_NAME:latest
                    """
                }
            }
        }

        stage('App Deploy') {
            steps {
                withAWS(credentials: 'aws-creds', region: "${AWS_REGION}") {
                    sh """
                        aws eks update-kubeconfig --region $AWS_REGION --name $CLUSTER_NAME
                        helm upgrade --install $HELM_RELEASE $HELM_CHART \
                            --set image.repository=$IMAGE_NAME \
                            --set image.tag=$IMAGE_TAG
                    """
                }
            }
        }
    }
}
