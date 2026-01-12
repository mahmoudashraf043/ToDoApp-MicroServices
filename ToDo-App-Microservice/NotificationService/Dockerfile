# Use base image with Java
FROM eclipse-temurin:17-jdk-jammy


#Set the working directory
WORKDIR /app

#Copy the jar file
COPY target/*.jar notification.jar

#EXPOSE the port
EXPOSE 8080

#Run the jar
ENTRYPOINT ["java", "-jar", "notification.jar"]