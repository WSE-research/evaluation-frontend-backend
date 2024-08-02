#Build Stage
FROM maven:latest AS build
WORKDIR /app
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN mvn clean install

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar backend.jar
ENTRYPOINT ["java", "--add-opens", "java.base/java.io=ALL-UNNAMED", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar","backend.jar"]
