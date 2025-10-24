FROM eclipse-temurin:17-jdk-jammy

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR
COPY target/price-0.0.1-SNAPSHOT.jar .

# Exponer el puerto
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/price-0.0.1-SNAPSHOT.jar"]
