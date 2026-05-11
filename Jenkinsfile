pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'your-registry.com'
        DOCKER_CREDENTIALS = credentials('docker-registry-creds')
        APP_NAME = 'beej-ecommerce'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean compile'
                }
            }
        }
        
        stage('Test Backend') {
            steps {
                dir('backend') {
                    sh 'mvn test'
                }
                publishTestResults testResultsPattern: 'backend/target/surefire-reports/*.xml'
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm ci'
                    sh 'npm run build'
                }
            }
        }
        
        stage('Test Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm test -- --coverage --watchAll=false'
                }
                publishTestResults testResultsPattern: 'frontend/coverage/junit.xml'
            }
        }
        
        stage('Security Scan') {
            steps {
                dir('backend') {
                    sh 'mvn dependency-check:check'
                }
                dir('frontend') {
                    sh 'npm audit --audit-level moderate'
                }
            }
        }
        
        stage('Build Docker Images') {
            parallel {
                stage('Backend Image') {
                    steps {
                        dir('backend') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/${APP_NAME}-backend:${BUILD_NUMBER}")
                                image.push()
                                image.push('latest')
                            }
                        }
                    }
                }
                stage('Frontend Image') {
                    steps {
                        dir('frontend') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/${APP_NAME}-frontend:${BUILD_NUMBER}")
                                image.push()
                                image.push('latest')
                            }
                        }
                    }
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                sh '''
                    docker-compose -f docker-compose.staging.yml down
                    docker-compose -f docker-compose.staging.yml pull
                    docker-compose -f docker-compose.staging.yml up -d
                '''
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            input {
                message "Deploy to production?"
            }
            steps {
                sh '''
                    docker-compose -f docker-compose.prod.yml down
                    docker-compose -f docker-compose.prod.yml pull
                    docker-compose -f docker-compose.prod.yml up -d
                '''
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            slackSend(
                channel: '#deployments',
                color: 'good',
                message: "✅ ${env.APP_NAME} deployed successfully!"
            )
        }
        failure {
            slackSend(
                channel: '#deployments',
                color: 'danger',
                message: "❌ ${env.APP_NAME} deployment failed!"
            )
        }
    }
}
