#!/usr/bin/env bash

# service_url.inc 에서 현재 서비스를 하고 있는 WAS의 포트 번호 가져오기
CURRENT_PORT=$(cat /etc/nginx/conf.d/api_service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

# 종료할 포트 탐색
if [ ${CURRENT_PORT} -eq 8081 ]; then
    TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo " nginx에 연결된 WAS가 없습니다. "
    exit 1
fi

# 현재 Target포트의 PID를 불러온다
TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# PID를 이용해 해당 포트 서버 Kill ( ! -z 변수명 => 변수 명이 비어있지 않을때를 의미
if [ ! -z ${TARGET_PID} ]; then
  echo "> ${TARGET_PORT}로 실행된 WAS를 종료합니다."
  sudo kill ${TARGET_PID}
fi
