pipeline {
	agent any

	// 전역변수 => ${SERVER_IP}
	environment {
		APP_DIR = "~/app"
		JAR_NAME = "SpringTotalProject-0.0.1-SNAPSHOT.war"
	}

	stages {
		/*
		연결 확인 = ngrok
		stage('Git Check Test') {
			steps {
				git branch: 'main',
				url: 'https://github.com/necteo/SpringTotalProject.git'
			}
		}

		stage('Check Git Info') {
			steps {
				sh '''
						echo "===Git Info==="
						git branch
						git log -1
					 '''
			}
		}
		*/
		// 감지 = main : push (commit)
		stage('Check Out') {
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}

		// gradlew build => war파일을 다시 생성
		stage('Gradle Permission') {
			steps {
				sh 'chmod +x gradlew'
			}
		}

		// build 시작
		stage('Gradle Build') {
			steps {
				sh './gradlew clean build'
			}
		}

		stage('Docker Build') {
			steps {
				sh '''
					docker build -t necteo/total-app:latest .
				'''
			}
		}

		// 실행 명령
		stage('Deploy to MiniKube') {
			steps {
				sh '''
					kubectl delete deployment total-app || true
					kubectl apply -f ~/k8s/deployment.yaml
					minikube start
					minikube service totalapp-service
				'''
			}
		}
	}

	post {
		success {
			echo '실행 성공'
		}
		failure {
			echo '실행 실패'
		}
	}
}
