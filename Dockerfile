# Usamos una imagen base ligera con Java 21
FROM amazoncorretto:21-alpine-jdk
# Directorio de trabajo dentro del contenedor
WORKDIR /app
# Copiamos el JAR generado por Maven al contenedor
COPY target/*.jar app.jar
# Informamos que la app escucha en el puerto 8080
EXPOSE 8080
# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
