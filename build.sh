#!/bin/bash

if [ -f .env ] ; then 
    export $(grep -E -v '^#' .env | sed -e 's/\s=/=/g' -e 's/=\s/=/g' | xargs)
fi

if [ ! -z $1 ] ; then
    cd services && mvn clean install -pl $1 -am 
fi