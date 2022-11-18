PREFIX=dev
NAME=spring-reactive
IMAGE_NAME=$PREFIX/$NAME

mvn -DskipTests clean package

pack config default-builder paketobuildpacks/builder:base

pack build $IMAGE_NAME --path .

docker run -p 9099:8082 -e SERVER_PORT=9099 $IMAGE_NAME
