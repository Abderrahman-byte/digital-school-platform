#!/bin/bash

# This script for developement only
# THIS SCRIPT must be replaced by docker-compose.yml

get_random_port () {
    read LOWERPORT UPPERPORT < /proc/sys/net/ipv4/ip_local_port_range
    while :
    do
            PORT="`shuf -i $LOWERPORT-$UPPERPORT -n 1`"
            ss -lpn | grep -q ":$PORT " || break
    done
    echo $PORT
}

cwd=$(pwd)
ip_address=$(dig +short $(hostname -f) | head -n 1)

# Remove run container
docker rm -f $(docker ps -q)

# Build Auth Service
docker build -t auth-service -f ./authentication-service/Dockerfile .

if [ $? -ne 0 ]; then
   echo "[ERROR] Authentication service build failed"
   exit 1
fi

# Start Social Service
docker build -t social-service -f ./social-service/Dockerfile .

if [ $? -ne 0 ]; then
   echo "[ERROR] Social service build failed"
   exit 1
fi

# Start School Management Service
docker build -t school-management -f ./school-management/Dockerfile .

if [ $? -ne 0 ]; then
   echo "[ERROR] School Management service build failed"
   exit 1
fi

docker run -dp $get_random_port:8080 --add-host database:$ip_address auth-service
docker run -dp $get_random_port:8080 --add-host database:$ip_address social-service
docker run -dp $get_random_port:8080 --add-host database:$ip_address school-management