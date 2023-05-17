#!/usr/bin/env bash

# service_url.inc 에서 현재 서비스를 하고 있는 WAS의 포트 번호 가져오기
CURRENT_PORT=$(cat /etc/nginx/conf.d/api_service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0


if [ ${CURRENT_PORT} -eq 8081 ]; then
    TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo "Nginx가 바라보는 Port가 설정되어 있지 않습니다. 확인해주세요."
    exit 1
fi


echo "서버 정상 동작 여부 검사를 시작하겠습니다."

# 해당 커맨드들을 10번씩 반복
for RETRY_COUNT in 1 2 3 4 5 6 7 8 9 10
do
    echo "> #${RETRY_COUNT} 번쨰 시도중입니다."
    # 테스트할 API 주소를 통해 http 상태 코드 가져오기 -s -o /dev/null은 출력 및 로그 저장하지 않는 것을 의미
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://3.38.5.26:${TARGET_PORT}/api/health)

	# RESPONSE_CODE의 http 상태가 200번인 경우 (10번 시도 하는 동안 200 코드가 와야한다)
    if [ ${RESPONSE_CODE} -eq 200 ]; then
        echo "새로운 WAS서버가 정상 구동됩니다."
        exit 0
    elif [ ${RETRY_COUNT} -eq 10 ]; then
        echo "서버가 정상 작동하지 않습니다."
        exit 1
    fi
    # 아직 열려있지 않았다면 sleep
    sleep 15
done