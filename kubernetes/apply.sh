#!/bin/bash
kubectl apply fileinfo-config.yaml
kubectl apply mongodb-secret.yaml
kubectl apply mongodb-depl.yaml
kubectl apply mongodb-expose.yaml
kubectl apply app-depl.yaml
kubectl apply app-expose.yaml
