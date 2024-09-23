# Use an official OpenJDK 23 image as the base image
FROM openjdk:23-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file created in the build step into the container
COPY build/libs/*.jar app.jar

# Expose port 8080 to the outside world (since Spring Boot runs on 8080 by default)
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]

