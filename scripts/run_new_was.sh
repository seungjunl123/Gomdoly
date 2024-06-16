# run_new_was.sh

#!/bin/bash

CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
#현재 실행되고 있는 포트는 service_url에서 포트번호를 가져와서 확인가능하다.

TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."
if [ ${CURRENT_PORT} -eq 8081 ]; then  # 현재 실행되는 포트가 8081 이라면 8082 으로 타겟포트설정
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then# 현재 실행되는 포트가 8082 이라면 8081 으로 타겟포트설정
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
#타겟포트를 이용하여 실행중인 프로그램이 있다면 종료시켜준다! (실행중인 프로그램 == 이전버전의 프로젝트)
if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

#nohup을 이용하여 프로젝트를 무중단실행시켜준다  (자세한건 구글링) , -Dserver.port를 이용하여 원하는포트로 프로젝트 실행가능

nohup java -jar -Dserver.port=${TARGET_PORT} /home/ubuntu/Gomdoly/build/libs/* > /home/ubuntu/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."

exit 0