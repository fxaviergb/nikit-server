# Build Stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/nikit-server.jar app.jar

# Configuración de zona horaria (opcional pero recomendado)
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/America/Guayaquil /etc/localtime && \
    echo "America/Guayaquil" > /etc/timezone

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
