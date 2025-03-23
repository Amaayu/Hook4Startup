# Build phase
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run phase with Java and Nginx
FROM ubuntu:22.04
WORKDIR /app

# Install Java and Nginx
RUN apt-get update && apt-get install -y openjdk-17-jdk nginx

# Copy Nginx config
COPY nginx.conf /etc/nginx/nginx.conf

# Copy compiled JAR from build phase
COPY --from=build /app/target/Hook4Startup-0.0.1-SNAPSHOT.jar Hook4Startup.jar

# Expose both Java and Nginx ports
EXPOSE 8081 8082

# Start both services (Java and Nginx)
CMD service nginx start && java -jar Hook4Startup.jar
