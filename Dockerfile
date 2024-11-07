# Use the official Maven image with Java 11 as a build stage
FROM maven:3.9.4-eclipse-temurin-21  AS build

# Set default working directory
WORKDIR /home/app

# Copy the project files into the container
COPY pom.xml /home/app

#cache dependencies
RUN mvn dependency:go-offline

COPY src /home/app/src

# Build the application
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Use OpenJDK for the final base image
FROM openjdk:21-jdk

# Copy the JAR from the build stage
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=jdbc:mariadb://172.17.0.2:3306
ENV SPRING_DATASOURCE_USERNAME=cg_quiz
ENV SPRING_DATASOURCE_PASSWORD="cg_quiz@123"

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]