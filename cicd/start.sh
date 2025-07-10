#!/bin/bash

# Permisos de ejecución: chmod +x cicd/start.sh

# Navegar a la carpeta del script
cd "$(dirname "$0")"

# Validar existencia de archivo .env
if [ ! -f ".env" ]; then
  echo "Archivo .env no encontrado en cicd/. Este archivo es requerido para definir variables de entorno."
  exit 1
fi

# Crear red td-network si no existe
NETWORK_NAME="td-network"
if ! docker network ls --format '{{.Name}}' | grep -wq "$NETWORK_NAME"; then
  echo "Red '$NETWORK_NAME' no existe. Creándola..."
  docker network create "$NETWORK_NAME"
else
  echo "Red '$NETWORK_NAME' ya existe."
fi

# Compilar el proyecto
echo ">> Compilando proyecto con Maven..."
cd ..
./mvnw clean package -DskipTests || exit 1

# Volver a la carpeta cicd
cd cicd

# Levantar servicios con docker compose
echo ">> Levantando contenedores usando variables de .env..."
docker compose --env-file .env up --build
