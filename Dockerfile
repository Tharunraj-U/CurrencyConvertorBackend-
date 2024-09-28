# Stage 1: Build the application
FROM maven:3.8.6-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download the dependencies
COPY pom.xml .

# Download dependencies (this will cache Maven dependencies to speed up builds)
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Package the application (produces a .jar file)
RUN mvn clean package -DskipTests

# Stage 2: Create the final image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the current stage
COPY --from=build /app/target/*.jar app.jar

# Copy the .env file (optional) if you want to include it within the Docker image
COPY .env /app/.env

# Expose the port the application runs on (default is 8080)
EXPOSE 8080

# Pass the .env file (optional) as environment variables to the container
# Alternatively, you can pass environment variables at runtime using docker run -e or in docker-compose.yml
ENV EXCHANGE_RATE_API_KEY=${EXCHANGE_RATE_API_KEY}
ENV EXCHANGE_RATE_BASE_URL=${EXCHANGE_RATE_BASE_URL}

# Entry point for the application (start the Spring Boot app)
ENTRYPOINT ["java", "-jar", "app.jar"]
