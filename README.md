# ! Important !
For Docker and Kubernetes/Helm, please only use the services and frontend from this repository. Otherwise, you will have to make adjustments to the request URLs in the frontend and in the application properties for database connection of the services.

# Overview

This repository contains the deployment configuration for all services. The included docker-compose.yml file sets up, builds, and launches all containers. The folder k8s contains the kubernetes manifests itself and the folder helm contains all helm configurations.

# Docker compose:

1. Build services

```
docker compose build
```

2. Run containers:

```
docker compose up -d
```

# Kubernetes:

1. Build and tag docker images for all services. Alternatively, you can use the already built images from docker hub (user: shivashantii). As a rule, you would not have to make any changes to do this. the images are already set correctly.

```
docker build -t <service-image-name> ./<Service-Folder>
```

```
docker image tag <service-image-name> <Your-Registry-Path>/<service-image-name>
```

2. Push images to docker hub. Alternatively, you can use the automatically built images from docker hub (user: shivashantii). As a rule, you would not have to make any changes to do this. the images are already set correctly.

```
docker image push <Your-Registry-Path>/<service-image-name>
```

3. Change directory to k8s

```
cd k8s/
```

4. Apply all kubernetes manifests in the underlying folders `deployments`, `services`, `pvc`

```
kubectl apply -f <manifest.yaml>
```

For apply the ingress.yaml you need the ingress-nginx ingress controller. You can install it with `helm`

```
helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace \
  --set controller.service.ports.http=80 \
  --set controller.service.ports.https=8443 \
  --set ingressClassResource.default=true
```

# Helm:
### ! Important !
If you have already manually installed the Nginx Helm controller in your Kubernetes cluster, you must remove it before running the Helm charts.


### Installtion steps
1. Complete steps 1 and 2 from the `Kubernetes` section if not already done. Alternatively, you can use the already built images from docker hub (user: shivashantii). As a rule, you would not have to make any changes to do this. the images are already set correctly.

2. Change directory to `helm`

```
cd helm
```

3. (Optional: only if you don't want to use our docker hub images). Enter your docker hub image name for each service in the `values.yml`. Example for servie-2:
``` Example
service2:
  image: <your-docker-hub-registry-path>/<service-image-name> <-- example: shivashantii/service-2
```

4. Install/Update dependencies

```
helm dependency update
```

5. Install Helm-Chart

```
helm install supermarket-chart .
```

For uninstall use:
```
helm uninstall supermarket-chart
```
