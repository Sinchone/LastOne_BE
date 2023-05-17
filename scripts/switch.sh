#!/usr/bin/env bash

# service_url.inc 에서 현재 서비스를 하고 있는 WAS의 포트 번호 가져오기
CURRENT_PORT=$(cat /etc/nginx/conf.d/api_service_url.inc  | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo " 현재 Nginx서버가 ${CURRENT_PORT} 포트를 가르키고 있습니다."

if [ ${CURRENT_PORT} -eq 8081 ]; then
    TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo " nginx에 연결된 WAS가 없습니다. "
    exit 1
fi

# 위 커맨드들을 통해 현재 타겟포트 가져오기

# $ service_url.inc 파일을 현재 바뀐 서버의 포트로 변경
echo "set \$service_url http://3.38.5.26:${TARGET_PORT}" | sudo tee /etc/nginx/conf.d/api_service_url.inc

echo "> ngnix가 ${TARGET_PORT} 포트 번호를 가르키고 있습니다.."

# nginx를 reload 실행
sudo service nginx reload

echo "> Nginx 를 reload 합니다."