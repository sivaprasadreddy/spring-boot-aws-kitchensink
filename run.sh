#!/bin/bash

declare project_dir=$(dirname "$0")
declare dc_file=${project_dir}/docker-compose.yml
declare deps="db localstack"
declare app="spring-boot-aws-kitchensink"

function build_api() {
    ./mvnw clean package -DskipTests
}

function build_image() {
    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=sivaprasadreddy/spring-boot-aws-kitchensink
}

function start() {
    echo "Starting dependent docker containers...."
    docker-compose -f "${dc_file}" up -d ${deps}
    docker-compose -f "${dc_file}" logs -f
}

function start_all() {
    build_api
    build_image
    echo "Starting app and dependent docker containers...."
    docker-compose -f "${dc_file}" up --build --force-recreate -d
    docker-compose -f "${dc_file}" logs -f
}

function stop() {
    echo "Stopping dependent docker containers...."
    docker-compose -f "${dc_file}" down
    docker-compose -f "${dc_file}" rm -f
}

function restart() {
    stop
    sleep 5
    start
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"