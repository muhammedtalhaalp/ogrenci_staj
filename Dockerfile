# Daha güvenilir ve yaygın bir base image kullanalım
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY target/ogrenci_staj-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
