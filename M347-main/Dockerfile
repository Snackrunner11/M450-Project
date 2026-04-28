#define base image
FROM eclipse-temurin:21-jdk
#copy the jar file to the container
COPY target/Lernen-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
#run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]