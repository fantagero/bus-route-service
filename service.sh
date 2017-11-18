#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

DOCKER_EXPOSE_PORT="8088:8088"

DOCKER_IMAGE_LABEL="goeuro/bus-route-service"

INPUT_DATA_FILE="$DIR/build/resources/main/bus_route_data_file.csv"

DOCKER_INPUT_DATA_FILE="/root/bus_route_data_file.csv"

# build local jar file
dev_build() {
    ./gradlew clean build
}

# runs application lacaly
dev_run() {
    if ["$2"]; then
        echo "Path to data file not provided default one will be used"
    else
        $INPUT_DATA_FILE=$2
    fi
    java -jar build/libs/bus-route-service-0.1.0.jar --routes.file=$INPUT_DATA_FILE
}

# builds docker image
docker_build() {
    if ["$2"]; then
        echo "Path to data file not provided default one will be used"
    else
        $INPUT_DATA_FILE=$2
    fi
    ./gradlew build buildDocker -DdataFileDockerPath=$INPUT_DATA_FILE
}

# runs docker image
docker_run() {
    docker run -p $DOCKER_EXPOSE_PORT -t $DOCKER_IMAGE_LABEL --routes.file=$DOCKER_INPUT_DATA_FILE
 }

# runs smoke tests
docker_smoke() {
 echo Smoke tests are not implemented yet
}

case $1 in
    dev_build)
        dev_build
        ;;
    dev_run)
        dev_run
        ;;
    docker_build)
        docker_build
        ;;
    docker_run)
        docker_run
        ;;
    docker_smoke)
        docker_smoke
        ;;
    *)
        echo "Supported actions: $0 {dev_build|dev_run|docker_build|docker_run|docker_smoke} INPUT_DATA_FILE"
esac