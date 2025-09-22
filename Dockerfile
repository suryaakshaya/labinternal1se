# Step 1: Use Maven to build the application
FROM maven:3.8-openjdk-11-slim AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy your Maven project files to the container
COPY pom.xml .
COPY src ./src

# Step 4: Run Maven to build the app (skip tests to speed up the build process)
RUN mvn clean package -DskipTests

# Step 5: Use a smaller base image to run the application
FROM openjdk:11-jre-slim

# Step 6: Set the working directory in the final container
WORKDIR /app

# Step 7: Copy the jar file from the build container
COPY --from=build /app/target/your-app.jar /app/your-app.jar

# Step 8: Expose the port that your app runs on (assuming 8080)
EXPOSE 8080

# Step 9: Run the app
CMD ["java", "-jar", "/app/your-app.jar"]
