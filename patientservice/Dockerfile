## Build stage
#FROM maven:3.9.9-eclipse-temurin-17 AS build
#WORKDIR /app
#
#COPY pom.xml .
#RUN mvn -B dependency:resolve dependency:resolve-plugins
#
#COPY src ./src
#RUN mvn clean package -DskipTests
#
## Run stage
#FROM eclipse-temurin:17-jre
#WORKDIR /app
#
## Create non-root user
#RUN addgroup --system app && adduser --system --ingroup app app
#USER app
#
## Copy jar (ensure single jar or set finalName in pom.xml)
#COPY --from=build /app/target/*.jar app.jar
#
## JVM optimized for containers
#ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]