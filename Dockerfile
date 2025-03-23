# Build Phase: Maven se JAR Build karna
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Poora code copy kar
COPY . .

# Maven build karke JAR generate kar
RUN mvn clean package -DskipTests

# Run Phase: Java ka container use karna
FROM openjdk:17-jdk-slim
WORKDIR /app

# Build phase se JAR ko copy karna
COPY --from=build /app/target/Hook4Startup-0.0.1-SNAPSHOT.jar Hook4Startup.jar

# Port expose karna
EXPOSE 8081

# Java application run karna
CMD ["java", "-jar", "Hook4Startup.jar"]

