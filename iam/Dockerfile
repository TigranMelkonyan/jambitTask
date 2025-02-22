FROM openjdk:11-jre-slim

WORKDIR /usr/app
COPY iam/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
