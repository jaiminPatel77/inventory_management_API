# ------------ Stage 1: Build the application ------------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy everything
COPY . .

# ✅ Make Maven Wrapper executable
RUN chmod +x mvnw

# Build using Maven wrapper
RUN ./mvnw clean package -DskipTests

# ------------ Stage 2: Run the application ------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for Spring Boot
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
