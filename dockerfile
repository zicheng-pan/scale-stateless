# service1/Dockerfile

FROM openjdk:17

COPY scale-single/target/scale-single-0.0.1-SNAPSHOT.jar /app/service.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/service.jar"]