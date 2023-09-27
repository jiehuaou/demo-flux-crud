# Base image
FROM adoptopenjdk:11-jdk-hotspot

# Set the working directory
WORKDIR /app

# Copy the application JAR file to the container
COPY target/demo-webflux-crud-0.0.1-SNAPSHOT.jar demo-webflux-crud-0.0.1-SNAPSHOT.jar

# Expose the desired port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "demo-webflux-crud-0.0.1-SNAPSHOT.jar"]
