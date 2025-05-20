#!/bin/bash


cd ./scale-single

echo "Running Maven clean install..."
mvn clean install
if [ $? -ne 0 ]; then
    echo "Maven build failed. Exiting."
    exit 1
fi

cd ..
echo "Building Docker image..."
docker build -t my-openjdk-app:1.0 .
if [ $? -ne 0 ]; then
    echo "Docker build failed. Exiting."
    exit 1
fi

