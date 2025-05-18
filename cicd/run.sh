#!/bin/bash

# Permisos de ejecucion
# chmod +x cicd/run.sh

# Navegar a carpeta cicd
cd "$(dirname "$0")"

# Construir JAR
echo ">> Compilando proyecto..."
cd ..
./mvnw clean package -DskipTests || exit 1

# Volver a cicd y levantar contenedores
cd cicd
echo ">> Levantando servicios con Docker Compose..."
docker-compose up --build
