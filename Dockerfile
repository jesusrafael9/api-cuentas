# Description: Dockerfile for the base image of the project
# author: Jesus Cardozo
# date: 2024-09-03
# version: 1.0
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado al contenedor
COPY target/cuentas-0.0.1-SNAPSHOT.jar /app/cuentas.jar
COPY src/main/resources/BaseDatos.sql /app/schems.sql

# Exponer el puerto en el que la aplicación se ejecuta
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/cuentas.jar"]

