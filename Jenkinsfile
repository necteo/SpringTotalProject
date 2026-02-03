pipeline {
	agent any

	environment {
        LANG = 'ko_KR.UTF-8'
        LC_ALL = 'ko_KR.UTF-8'
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
		
		stage('Docker Push') {
		    steps {
		        // 젠킨스에 등록한 자격 증명을 사용하여 로그인 및 푸시
		        withCredentials([usernamePassword(credentialsId: 'docker-hub-id', 
		                         passwordVariable: 'DOCKER_PASSWORD', 
		                         usernameVariable: 'DOCKER_USERNAME')]) {
		            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
		            sh "docker push necteo/total-app:latest"
		        }
		    }
		}

		// 실행 명령
		stage('Deploy to MiniKube') {
			steps {
				sh '''
					kubectl delete deployment total-app || true
					kubectl apply -f ~/k8s/deployment.yaml
					kubectl rollout restart deployment totalapp-dep
					kubectl get pods  # Pod가 잘 뜨는지 확인
					kubectl get svc   # 서비스 상태 확인loyment
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
