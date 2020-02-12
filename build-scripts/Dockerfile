FROM mongo:4.2
WORKDIR /tpcn/db/init
COPY mongodb/db-colls-init.js .
RUN mongo --host tpcn-mongodb db-colls-init.js

FROM openjdk:8
WORKDIR /tpcn/app
RUN mkdir logs
COPY server .
RUN ls -la
ENTRYPOINT [ "java", "-jar" ]