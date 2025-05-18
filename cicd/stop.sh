#!/bin/bash

echo "Buscando contenedores relacionados con 'nikit-server' y 'mongo'..."

containers=$(docker ps -a --filter "name=nikit-server" --filter "name=mongo" --format "{{.ID}}")

if [ -z "$containers" ]; then
  echo "No se encontraron contenedores para detener."
else
  echo "Deteniendo contenedores..."
  docker stop $containers

  echo "Eliminando contenedores..."
  docker rm $containers
fi

echo "Proceso completado."
