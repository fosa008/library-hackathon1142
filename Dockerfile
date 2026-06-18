# Use the new, supported image
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy everything
COPY . .

# Install Maven inside the container
RUN apt-get update && apt-get install -y maven

# Build the project
RUN mvn clean package -DskipTests

# Run the application
ENTRYPOINT ["java","-jar","target/library-vibe-system-0.0.1-SNAPSHOT.jar"]