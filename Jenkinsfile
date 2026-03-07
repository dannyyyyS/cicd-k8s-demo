pipeline {
    agent any
    tools {
        maven 'Maven3'
    }
    environment {
        REGISTRY = 'docker.io'
        IMAGE_REPO = 'smsoooo/cicd-k8s-demo'
        IMAGE_TAG = "1.0-${env.GIT_COMMIT.take(7)}"
        FULL_IMAGE = "${REGISTRY}/${IMAGE_REPO}:${IMAGE_TAG}"
        LATEST_IMAGE = "${REGISTRY}/${IMAGE_REPO}:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Rename target/app.jar') {
            steps {
                sh '''
                    set -e
                    echo "Finding Spring Boot jar in build result..."
                    JAR_FILE=$(find target -maxdepth 1 -type f -name "*.jar" \
                      ! -name "app.jar" \
                      ! -name "*.original" | head -n 1)

                    if [ -z "$JAR_FILE" ]; then
                      echo "ERROR: No jar file found in target/"
                      exit 1
                    fi

                    echo "Using jar: $JAR_FILE"
                    cp "$JAR_FILE" target/app.jar
                    ls -lh target
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build \
                      --build-arg JAR_FILE=target/app.jar \
                      -t ${FULL_IMAGE} \
                      .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                // use docker credentials
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh '''
                        set +x
                        echo "$DOCKER_PASSWORD" | docker login ${REGISTRY} -u "$DOCKER_USERNAME" --password-stdin
                        docker push ${FULL_IMAGE}
                        docker push ${LATEST_IMAGE}
                        docker logout ${REGISTRY}
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "Build and push succeeded: ${FULL_IMAGE}"
        }
        failure {
            echo "Build or push failed."
        }
    }
}