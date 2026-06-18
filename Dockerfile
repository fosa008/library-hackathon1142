# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the project using Maven
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Run the jar file
ENTRYPOINT ["java","-jar","target/library-vibe-system-0.0.1-SNAPSHOT.jar"]