# build with local minikube docker

eval $(minikube docker-env)

docker build -t my-java-app:v1.0 .

# deploy to k8s if image is in remote docker
kubectl create deployment my-java-deployment --image=my-java-app

# deploy to k8s with local image （minikube docker）

kubectl apply -f k8s/java-app.yml

# export to service
kubectl expose deployment my-java-app  --port=8080 --target-port=8080 --type=ClusterIP --name=my-java-svc

# export to ingress
kubectl apply -f k8s/java-ingress.yml

# test connection
curl --resolve "hello-world.info:80:$(minikube ip)" -i http://hello-world.info/crud

curl --resolve "hello-world.info:80:$(minikube ip)" -i http://hello-world.info/crud/item/TLG-SKU-0082

