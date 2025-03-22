# Build phase
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run phase
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Hook4Startup-0.0.1-SNAPSHOT.jar Hook4Startup.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "Hook4Startup.jar"]
