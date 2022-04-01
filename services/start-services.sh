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

if [ "$EUID" -ne 0 ]
then
   echo "[ERROR] You must root to execute this script"
   exit 1
fi

cwd=$(pwd)
ip_address=$(hostname -I | awk '{ print $1}')

# Remove run container
docker rm -f $(docker ps -q) 2>/dev/null 1>/dev/null

# Build Auth Service
auth_service_build_start=$(date +%s)
docker build -t auth-service -f ./authentication-service/Dockerfile .

if [ $? -ne 0 ]; then
   echo "[ERROR] Authentication service build failed"
   exit 1
fi

auth_service_build_time=$(expr $(date +%s) - $auth_service_build_start)

# Start Social Service
social_service_build_start=$(date +%s)
docker build -t social-service -f ./social-service/Dockerfile .

if [ $? -ne 0 ]; then
   echo "[ERROR] Social service build failed"
   exit 1
fi

social_service_build_time=$(expr $(date +%s) - $social_service_build_start)

# Start School Management Service
school_service_build_start=$(date +%s)
docker build -t school-management -f ./school-management/Dockerfile .

if [ $? -ne 0 ]; then
   echo "[ERROR] School Management service build failed"
   exit 1
fi

school_service_build_time=$(expr $(date +%s) - $school_service_build_start)

docker run -dp $get_random_port:8080 --add-host database:$ip_address auth-service
docker run -dp $get_random_port:8080 --add-host database:$ip_address social-service
docker run -dp $get_random_port:8080 --add-host database:$ip_address school-management

echo -e "\nAuthenfication service build time : $(date -d @$auth_service_build_time +%T)"
echo "Social service build time : $(date -d @$social_service_build_time +%T)"
echo "School service build time : $(date -d @$school_service_build_time +%T)"
echo "Total: $(date -d @$(expr $(expr $auth_service_build_time + $social_service_build_time) + $school_service_build_time) +%T)"

cd $cwd