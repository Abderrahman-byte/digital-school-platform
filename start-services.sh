#!/bin/bash

docker-compose up --build -d postgres

./build-all.sh

docker-compose up -d --build