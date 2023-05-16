#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app" # 프로젝트 루트
JAR_FILE="$PROJECT_ROOT/api-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=api-prod" # JAR_FILE (어쩌구저쩌구.jar)

# 현재 nginx에서 서비스 하고 있는 WAS의 포트 번호 가져오기
CURRENT_PORT=$(cat /etc/nginx/conf.d/api_service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0
echo "> 현재 ngnix가 가르키고 있는 포트 번호는 ${CURRENT_PORT} 입니다."

# 현재 실행하고 있는 WAS 서버와 다른 포트로 타겟 포트 설정
if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "Nginx가 가르키는 WAS가 없습니다."
fi

# 현재 Target포트의 PID를 불러온다
TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# PID를 이용해 해당 포트 서버 Kill ( ! -z 변수명 => 변수 명이 비어있지 않을때를 의미
if [ ! -z ${TARGET_PID} ]; then
  echo "> ${TARGET_PORT}로 실행된 WAS를 종료합니다."
  sudo kill ${TARGET_PID}
fi

# 타켓 포트에 jar파일을 이용해 새로운 서버 실행
nohup java -jar -Dserver.port=${TARGET_PORT} ${JAR_FILE} > /home/ubuntu/nohup.out 2>&1 &
echo "> 새로운 WAS 서버를 포트번호 ${TARGET_PORT}에 실행시키겠습니다."
exit 0