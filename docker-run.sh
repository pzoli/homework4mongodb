#!/bin/bash
docker network create my-net

docker run -e "MONGO_INITDB_ROOT_USERNAME=admin" \
-e "MONGO_INITDB_ROOT_PASSWORD=example" \
-d \
--network my-net --net-alias=mongoserver \
-p 27017:27017 -t mongo:latest


docker run -e "MONGODB_USER=admin" \
-e "MONGODB_PASSWORD=example" \
-e "MONGODB_HOST=mongoserver" \
-e "MONGODB_PORT=27017" \
-e "MONGODB_DATABASE=fileinfo" \
-d \
--network my-net \
 -p 8080:8080 -t pzoli77/fileinfo:latest
