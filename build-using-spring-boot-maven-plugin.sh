PREFIX=dev
NAME=spring-reactive
IMAGE_NAME=$PREFIX/$NAME

mvn -DskipTests clean package spring-boot:build-image -DimageName=$IMAGE_NAME

docker run -p 9099:8082 -e SERVER_PORT=9099 $IMAGE_NAME
