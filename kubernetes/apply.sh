#!/bin/bash
kubectl apply -f fileinfo-config.yaml
kubectl apply -f mongodb-secret.yaml
kubectl apply -f mongodb-depl.yaml
kubectl apply -f mongodb-expose.yaml
kubectl apply -f app-depl.yaml
kubectl apply -f app-expose.yaml
kubectl apply -f ingress.yaml