FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar gateway.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","gateway.jar"]