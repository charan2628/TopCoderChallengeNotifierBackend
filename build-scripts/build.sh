#!/bin/bash

if (($# != 2)); then
    echo "Usage: ./build.sh db-data-directory app-log-directory version"
    exit 1
fi

if [ -e ./server/TopCoderChallengeNotifierBackend-${3}.jar ]; then
  :
else
  echo "TopCoderChallengeNotifierBackend-${3}.jar is missing in server folder."
  exit 1
fi

if [ "$(sudo docker network ls | grep "tpcn-net" | grep "bridge")" ]; then
    echo "Using existing tpcn-net network."
else
    sudo docker network create --driver bridge tpcn-net
fi

if [ "$(sudo docker container ls | grep "tpcn-mongodb")" ]; then
    echo "tpcn-mongodb already present using it."
else
    sudo docker run --rm -d \
    --network tpcn-net \
    --mount type=bind,src=${1},target=/data/db \
    --name tpcn-mongodb mongo:4.2
fi

sudo docker build --network tpcn-net --tag tpcn-api:${3} .

sudo docker run --rm -d --network tpcn-net -p 8080:8080 \
--mount type=bind,src=${2},target=/tpcn/app/logs \
--name tpcn-api-run tpcn-api:${3} -DLOG_HOME="/tpcn/app/logs" \
TopCoderChallengeNotifierBackend-${3}.jar \
--db.host=tpcn-mongodb --JKS_KEYSTORE_ALIAS=dummy --JKS_KEYSTORE_FILE=dummy.jks \
--JKS_KEYSTORE_PASSWORD=dummy --APP_SENDER_MAIL=dummy@gmail.com \
--APP_SENDER_MAIL_PASSWORD=dummy --schedule_rate=1800000 \
--server.servlet.contextPath=/v1
