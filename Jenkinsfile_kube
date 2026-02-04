pipeline {
	agent any

	environment {
        LANG = 'ko_KR.UTF-8'
        LC_ALL = 'ko_KR.UTF-8'
    }

	stages {
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
					docker build -t necteo/total-app:${BUILD_NUMBER} .
				'''
			}
		}
		
		stage('Docker Push') {
		    steps {
		        // 젠킨스에 등록한 자격 증명을 사용하여 로그인 및 푸시
		        withCredentials([usernamePassword(credentialsId: 'docker-hub-id', 
		                         passwordVariable: 'DOCKER_PASSWORD', 
		                         usernameVariable: 'DOCKER_USERNAME')]) {
		            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
		            sh "docker push necteo/total-app:${BUILD_NUMBER}"
		        }
		    }
		}

		// 실행 명령
		stage('Deploy to MiniKube') {
			steps {
				sh '''
					kubectl set image deployment/totalapp-deployment totalapp=necteo/total-app:${BUILD_NUMBER}
        	kubectl rollout status deployment/totalapp-deployment --timeout=120s
				'''
			}
		}
	}

	post {
		success {
			echo '실행 성공'
		}
		failure {
			sh 'kubectl rollout undo deployment/totalapp-deployment || true'
			echo '실행 실패'
		}
	}
}
