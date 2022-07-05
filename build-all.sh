#!/bin/bash

if [ -f .env ] ; then 
    export $(grep -E -v '^#' .env | sed -e 's/\s=/=/g' -e 's/=\s/=/g' | xargs)
fi

cd services

mvn clean

mvn install -pl discovery-service -am
mvn install -pl api-gateway -am
mvn install -pl authentication-service -am
mvn install -pl social-service -am
mvn install -pl school-management -am