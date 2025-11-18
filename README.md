<p> <img src="https://img.shields.io/badge/AWS-Cloud-orange?logo=amazon-aws&logoColor=white" /> <img src="https://img.shields.io/badge/EKS-Kubernetes-blue?logo=kubernetes&logoColor=white" /> <img src="https://img.shields.io/badge/Jenkins-CI%2FCD-red?logo=jenkins&logoColor=white" /> <img src="https://img.shields.io/badge/Docker-Container-2496ED?logo=docker&logoColor=white" /> <img src="https://img.shields.io/badge/Kubernetes-Cluster-326CE5?logo=kubernetes&logoColor=white" /> <img src="https://img.shields.io/badge/Java-App-007396?logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Maven-Build-C71A36?logo=apache-maven&logoColor=white" /> <img src="https://img.shields.io/badge/Helm-Charts-0F1689?logo=helm&logoColor=white" /> <img src="https://img.shields.io/badge/DockerHub-Registry-2496ED?logo=docker&logoColor=white" /> <img src="https://img.shields.io/badge/EKS-Deployment-blue?logo=kubernetes&logoColor=white" /> </p>
📘 Project Overview

The main objectives of this project:

CI/CD Pipeline with Jenkins, Docker, Helm, and AWS EKS
📌 Project Overview

This project demonstrates a complete CI/CD pipeline for deploying a Java web application to an AWS EKS Kubernetes cluster using:

Jenkins

Maven

Docker

Docker Hub

Helm

AWS EKS

The pipeline automatically:

Clones the latest code from GitHub

Builds the application (WAR/JAR)

Builds & pushes Docker image

Deploys to EKS using Helm

Makes the application accessible using Service or Ingress

🚀 Technologies Used
Tool	Purpose
Jenkins	CI/CD Orchestration
Maven	Build & Package Java App
Docker	Containerization
Helm	Kubernetes Deployment
AWS EKS	Kubernetes Cluster
Docker Hub	Container Registry
🧱 Repository Structure
.
├── src/
├── pom.xml
├── Dockerfile
├── helm-chart/
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates/
│       ├── deployment.yaml
│       ├── service.yaml
│       └── ingress.yaml  (optional)
└── Jenkinsfile

⚙️ Jenkins Pipeline Stages
1️⃣ Clone Code
stage('Code Cloning') {
    steps {
        git credentialsId: 'your-cred-id', url: 'https://github.com/mohammadshoaib8/javawebapptest-jenkins.git'
    }
}

2️⃣ Build Application using Maven
stage('Build') {
    steps {
        sh 'mvn clean package -DskipTests'
    }
}


This produces a WAR/JAR file in:
target/yourfile.war

3️⃣ Build Docker Image
stage('Build Docker Image') {
    steps {
        sh """
            docker build -t $IMAGE_NAME:$IMAGE_TAG .
            docker tag $IMAGE_NAME:$IMAGE_TAG $IMAGE_NAME:latest
        """
    }
}

4️⃣ Push Docker Image to Docker Hub
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

5️⃣ Deploy to EKS using Helm
stage('Deploy using Helm') {
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


This updates the Kubernetes deployment with the new container image.

☸️ How to Access the Application

After deployment:

1️⃣ Check Service
kubectl get svc


If LoadBalancer → public URL

http://<EXTERNAL-IP>/


If NodePort →

http://<node-public-ip>:<node-port>/

2️⃣ If Using Ingress
kubectl get ingress


Access using ALB/Nginx URL:

http://<LOADBALANCER-DNS>/

📦 Helm Chart (Example)
values.yaml
image:
  repository: msshoaib2255457/javajenkinstest
  tag: latest

service:
  type: LoadBalancer
  port: 80

🐳 Dockerfile Example
FROM tomcat:9-jdk17

COPY target/*.war /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]

🛠 Prerequisites

AWS EKS Cluster created

kubectl installed

Helm installed

Docker installed

Jenkins with plugins:

Pipeline

Docker

Docker Pipeline

AWS Credentials

Kubernetes CLI

Credentials configured:

GitHub

Docker Hub

AWS IAM User (programmatic access)

🎯 Final Outcome

Once the pipeline completes:

✔ Latest Git code deployed
✔ App container pushed to Docker Hub
✔ EKS deployment updated with new image
✔ Application publicly accessible
