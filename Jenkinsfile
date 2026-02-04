pipeline {
    agent any

    environment {
        LANG = 'ko_KR.UTF-8'
        LC_ALL = 'ko_KR.UTF-8'
    }

    stages {
        stage('Check Out') {
            steps {
                checkout scm
            }
        }

        stage('Gradle Permission') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Gradle Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t necteo/total-app:${BUILD_NUMBER} ."
            }
        }

        stage('Blue-Green Deploy') {
            steps {
                sh '''#!/bin/bash
                    # 현재 Nginx가 바라보는 포트 확인
                    CURRENT_PORT=$(grep -oP "server localhost:\\K[0-9]+" /etc/nginx/conf.d/totalapp.conf)

                    if [ "$CURRENT_PORT" == "9090" ]; then
                        NEW_PORT=9091
                        OLD_PORT=9090
                    else
                        NEW_PORT=9090
                        OLD_PORT=9091
                    fi

                    echo "현재: $CURRENT_PORT → 새로운: $NEW_PORT"

                    # 새 컨테이너 실행
                    docker rm -f app-$NEW_PORT || true
                    docker run -d --name app-$NEW_PORT -p $NEW_PORT:9090 necteo/total-app:${BUILD_NUMBER}

                    # Ready 대기 (최대 90초)
                    echo "앱 시작 대기 중..."
                    for i in $(seq 1 45); do
                        if curl -s http://localhost:$NEW_PORT/actuator/health/readiness | grep -q "UP"; then
                            echo "새 컨테이너 Ready!"
                            break
                        fi
                        sleep 2
                    done

                    # Nginx 포트 전환
                    sudo sed -i "s/localhost:[0-9]*/localhost:$NEW_PORT/" /etc/nginx/conf.d/totalapp.conf
                    sudo nginx -s reload

                    echo "트래픽 전환 완료"

                    # 잠시 대기 후 기존 컨테이너 종료
                    sleep 5
                    docker stop app-$OLD_PORT || true
                    docker rm app-$OLD_PORT || true

                    echo "배포 완료: localhost:$NEW_PORT"
                '''
            }
        }
    }

    post {
        success {
            echo '배포 성공'
        }
        failure {
            echo '배포 실패'
        }
    }
}