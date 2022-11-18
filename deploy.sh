IMAGE_NAME=mt/bootiful/spring-reactive:latest
NS=dev
APP_NAME=customers

mvn -DskipTests clean package spring-boot:build-image -Dspring-boot.build-image.imageName=$IMAGE_NAME

#kubectl get ns | grep $NS || kubectl craate ns $NS
mkdir -p k8s
kubectl get ns/$NS || kubectl craate ns $NS -o yaml >k8s/na.yaml
kubectl get -n $NS deployments/$APP_NAME || kubectl -n $NS create deployment --image=$IMAGE_NAME $APP_NAME -o yaml >k8s/deployment.yaml

#Now if we want to port forward we can use the following command
## kubectl -n dev port-forward deployment/customers 9099:9100

kubectl -n $NS expose deployment $APP_NAME --port=9100 -o yaml >k8s/service.yaml
