#!/bin/bash

# automatic VM Setup Script (Ubuntu 22.04)
# Run with: sudo ./setup_vm.sh

echo "--- 1. System update ---"
sudo apt update && sudo apt upgrade -y

echo "--- 2. Installation Java 17 (JDK) ---"
sudo apt install -y openjdk-17-jdk
# Set Java 17 as default
sudo update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/bin/java
sudo update-alternatives --set javac /usr/lib/jvm/java-17-openjdk-amd64/bin/javac
java -version

echo "--- 3. Installation Maven ---"
sudo apt install -y maven
mvn -version

echo "--- 4. Installation Docker ---"
sudo apt install -y docker.io
sudo systemctl start docker
sudo systemctl enable docker
# Adds the current user to the docker group (to not always have to use sudo)
sudo usermod -aG docker $USER

echo "--- 5. Installation Docker Compose ---"
sudo apt install -y docker-compose-plugin
docker compose version

echo "--- 6. Creating Folders for Volumes ---"
# Create folders for persistent data if they don't exist
mkdir -p mysql-data
mkdir -p notification-data
mkdir -p booking-data

echo "--- SETUP COMPLETED! ---"
echo "Now you can do 'git pull' and then 'sudo docker compose up --build -d'"