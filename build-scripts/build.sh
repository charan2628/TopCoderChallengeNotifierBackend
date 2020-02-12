#!/bin/bash

sudo docker network create --driver bridge tpcn-net

sudo docker run --rm -d \
--network tpcn-net \
--mount type=bind,src=${1},target=/data/db \
--name tpcn-mongodb mongo:4.2

sudo docker build --network tpcn-net --tag tpcn-api:${3} .

sudo docker run --rm --network tpcn-net -p 8080:8080 \
--mount type=bind,src=${2},target=/tpcn/app/logs \
--name tpcn-api-run tpcn-api:${3} -DLOG_HOME="/tpcn/app/logs" \
TopCoderChallengeNotifierBackend-${3}.jar \
--db.host=tpcn-mongodb --JKS_KEYSTORE_ALIAS=dummy --JKS_KEYSTORE_FILE=dummy-key.jks \
--JKS_KEYSTORE_PASSWORD=dummy --APP_SENDER_MAIL=dummy@gmail.com \
--APP_SENDER_MAIL_PASSWORD=dummy --schedule_rate=1800000 