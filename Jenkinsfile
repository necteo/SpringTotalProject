pipeline {
	agent any
	
	environment {
		DOCKER_IMAGE = "necteo/total-app"
		DOCKER_TAG = "latest"
		CONTAINER = "total-app"
		EC2_HOST = "34.224.165.166"
		EC2_USER = "ubuntu"
		PORT = "9090"
		COMPOSE_FILE = "docker-compose.yml"
	}
	
	stages {
		// Git 연결 => Git 주소
		stage('Checkout') {
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}
		// 배포판 만들기
		stage('Gradle Build') {
			steps {
				echo 'Gradle Build'
				sh '''
						chmod +x gradlew
						./gradlew clean build -x test
					 '''
			}
		}
		
		stage('Docker Build') {
			steps {
				echo 'Docker Image Build'
				sh '''
						docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
					 '''
			}
		}
		
		stage('DockerHub Login') {
			steps {
				echo 'DockerHub Login'
				withCredentials([usernamePassword(
					credentialsId: 'dockerhub-credential',
					usernameVariable: 'DOCKER_ID',
					passwordVariable: 'DOCKER_PW'
				)]) {
					sh 'echo $DOCKER_PW | docker login -u $DOCKER_ID --password-stdin'
				}
			}
		}
		
		stage('DockerHub Push') {
			steps {
				echo 'DockerHub Push'
				sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
			}
		}
		/*
		stage('Add SSH key') {
			steps {
				echo 'Add SSH key'
				sshagent(credentials: ['SERVER_KEY']) {
					sh """
							ssh-keyscan -t ed25519 ${EC2_HOST} >> ~/.ssh/known_hosts
							
							ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} << 'EOF'
								docker stop ${CONTAINER} || true
								docker rm ${CONTAINER} || true
								docker pull ${DOCKER_IMAGE}:${DOCKER_TAG}
								docker run --name ${CONTAINER} -d -p ${PORT}:${PORT} ${DOCKER_IMAGE}:${DOCKER_TAG}
EOF
						 """
				}
			}
		}
		*/
		stage('Add SSH key') {
			steps {
				echo 'Add SSH key'
				sshagent(credentials: ['SERVER_KEY']) {
					sh """
							ssh-keyscan -t ed25519 ${EC2_HOST} >> ~/.ssh/known_hosts
						 """
				}
			}
		}
		
		stage('Docker Compose Down') {
			steps {
				echo 'docker-compose down'
				sh 'docker compose -f ${COMPOSE_FILE} down || true'
			}
		}
		
		stage('Docker Stop and Remove') {
			steps {
				echo 'docker stop rm'
				sh '''
						docker stop ${CONTAINER} || true
						docker rm ${CONTAINER} || true
						docker pull ${DOCKER_IMAGE}
					 '''
			}
		}
		
		stage('Docker Compose Up') {
			steps {
				echo 'docker-compose up'
				sh 'docker compose -f ${COMPOSE_FILE} up -d'
			}
		}
		
		/*stage('Docker Run') {
			steps {
				echo 'Docker Run'
				sh '''
						docker stop ${CONTAINER_NAME} || true
						docker rm ${CONTAINER_NAME} || true
						
						docker pull ${DOCKER_IMAGE}:${DOCKER_TAG}
						
						docker run --name ${CONTAINER_NAME} -it -d -p 9090:9090 ${DOCKER_IMAGE}:${DOCKER_TAG}
					 '''
			}
		}*/
	}
	
	post {
		success {
			echo 'CI/CD 실행 성공'
		}
		failure {
			echo 'CI/CD 실행 실패'
		}
	}
}