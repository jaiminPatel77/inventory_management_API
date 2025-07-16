# Use Java 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy Maven build file and source code
COPY pom.xml ./
COPY src ./src

# Install Maven and build the project (skipping tests)
RUN apt-get update && apt-get install -y maven && \
    mvn clean install -DskipTests

# Run the built JAR
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
