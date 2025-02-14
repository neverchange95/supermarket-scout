# Use an official Maven image as the base image
FROM maven:3.9.8-eclipse-temurin-21 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY price_comparison_app/pom.xml .
COPY price_comparison_app/src ./src
# Build the application using Maven
RUN mvn clean package
# Use an official OpenJDK image as the base image
FROM openjdk:21
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/price_comparison_app-0.0.1-SNAPSHOT.jar .
# Set the command to run the application
CMD ["java", "-jar", "price_comparison_app-0.0.1-SNAPSHOT.jar"]
