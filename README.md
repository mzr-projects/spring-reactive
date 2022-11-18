### Starting Kubernetes
````shell
- minikube start
- minikube config set vm-driver virtualbox
- kubectl config use-context minikube
- kubectl cluster-info
- minikube dashboard
````

### Make docker image and deploy it into Kubernetes
 We must create a spring-reactive directory inside the minikube and mount our workspace into it.
 We can create the specified directory using "minikube ssh"

````shell
- minikube mount B:\SourceCodes\Java\spring-boot\spring-reactive:/home/docker/spring-reactive
````

We go to the minikube vm and cd to the spring-reactive directory then build our image inside it

```shell
-  minikube ssh
-  docker build --file=Dockerfile --tag=spring-reactive:latest --rm=true .
```

Now we back to the host and run the following command, this command gives up a yml file that we can provide for the command number 2 and create a deployment

```shell
-  kubectl create deployment spring-reactive --image=spring-reactive:latest -o yaml --dry-run
-  kubectl apply -f .\spring-reactive.yml
```

### Expose our service over minikube(Kubernetes)

````shell
- kubectl expose deployment spring-reactive --type=NodePort
````

### Check that out service is available
````shell
- kubectl get services
````

### Here we connect to the service
````shell
- minikube service spring-reactive
````