# Use official OpenJDK image from Docker Hub
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the jar file from the host to the container
COPY target/income-podium-doc-extractor.jar /app/income-podium-doc-extractor.jar

# Expose the port your application runs on
ENV APP_PORT=8080
ENV AWS_SQS_REGION=us-east-1

# Run the application
ENTRYPOINT ["java", "-jar", "income-podium-doc-extractor.jar"]
