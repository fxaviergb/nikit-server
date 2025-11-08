FROM eclipse-temurin:17-jdk-alpine

# Instalar tzdata y establecer zona horaria
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/America/Guayaquil /etc/localtime && \
    echo "America/Guayaquil" > /etc/timezone

VOLUME /tmp
ARG JAR_FILE=target/nikit-server.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Duser.timezone=America/Guayaquil", "-jar", "/app.jar"]
