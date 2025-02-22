FROM openjdk:11-jre-slim

WORKDIR /usr/app
COPY feedback/infrastructure/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
