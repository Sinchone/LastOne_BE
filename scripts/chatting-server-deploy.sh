#!/usr/bin/env bash

# ---------------- 서버 실행 -----------------------------

PROJECT_ROOT="/home/ubuntu/app" # 프로젝트 루트
JAR_FILE="$PROJECT_ROOT/chatting-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=chatting-prod" # JAR_FILE (어쩌구저쩌구.jar)

# 현재 nginx에서 서비스 하고 있는 WAS의 포트 번호 가져오기
CURRENT_PORT=$(cat /etc/nginx/conf.d/chatting_service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0
END_PORT=0
echo "> 현재 ngnix가 가르키고 있는 포트 번호는 ${CURRENT_PORT} 입니다."

# 현재 실행하고 있는 WAS 서버와 다른 포트로 타겟 포트 설정
if [ ${CURRENT_PORT} -eq 8083 ]; then
  TARGET_PORT=8084
  END_PORT=8083
elif [ ${CURRENT_PORT} -eq 8084 ]; then
  TARGET_PORT=8083
  END_PORT=8084
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
echo "> 새로운 WAS 서버를 포트번호 ${TARGET_PORT}에 실행시키겠습니다."
nohup java -jar -Dserver.port=${TARGET_PORT} ${JAR_FILE} > /home/ubuntu/nohup.out 2>&1 &



# ---------------- 서버 상태 체크 -----------------------------

echo "서버 정상 동작 여부 검사를 시작하겠습니다."
# 해당 커맨드들을 10번씩 반복
for RETRY_COUNT in 1 2 3 4 5 6 7 8 9 10
do
    echo "> #${RETRY_COUNT} 번쨰 시도중입니다."
    # 테스트할 API 주소를 통해 http 상태 코드 가져오기 -s -o /dev/null은 출력 및 로그 저장하지 않는 것을 의미
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://3.38.5.26:${TARGET_PORT}/chat/health)

	# RESPONSE_CODE의 http 상태가 200번인 경우 (10번 시도 하는 동안 200 코드가 와야한다)
    if [ ${RESPONSE_CODE} -eq 200 ]; then
        echo "새로운 WAS서버가 정상 구동됩니다."
        break
    elif [ ${RETRY_COUNT} -eq 10 ]; then
        echo "서버가 정상 작동하지 않습니다."
        exit 1
    fi
    # 아직 열려있지 않았다면 sleep
    sleep 15
done


# ---------------- Nginx 리버스 프록시 타겟 포트 변경 -----------------------------

# $ api_service_url.inc 파일을 현재 바뀐 서버의 포트로 변경
echo "set \$chatting_service_url http://3.38.5.26:${TARGET_PORT};" | sudo tee /etc/nginx/conf.d/api_service_url.inc
echo "> ngnix가 ${TARGET_PORT} 포트 번호를 가르키고 있습니다."

# nginx를 reload 실행
echo "> Nginx 를 reload 합니다."
sudo systemctl reload nginx


# ---------------- 기존 서버 종료 -----------------------------

# 현재 Target포트의 PID를 불러온다
TARGET_PID=$(lsof -Fp -i TCP:${END_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# PID를 이용해 해당 포트 서버 Kill ( ! -z 변수명 => 변수 명이 비어있지 않을때를 의미
if [ ! -z ${TARGET_PID} ]; then
  echo "> ${END_PORT}로 실행된 WAS를 종료합니다."
  sudo kill ${TARGET_PID}
fi

