FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /
COPY /passanger-transportation-configuration /passanger-transportation-configuration
COPY /passenger-transportation-service-core /passenger-transportation-service-core
COPY /passenger-transportation-service-input-port /passenger-transportation-service-input-port
COPY /passenger-transportation-service-output-port /passenger-transportation-service-output-port
COPY /passenger-transportation-service-spring-web-adapter /passenger-transportation-service-spring-web-adapter
COPY /passenger-transportation-shared /passenger-transportation-shared
COPY /passenger-transportation-cache-adapter /passenger-transportation-cache-adapter
COPY /passenger-transportation-jpa-graph-adapter /passenger-transportation-jpa-graph-adapter
COPY /pom.xml /

RUN mvn -f /pom.xml clean package


FROM openjdk:21-jdk-slim
COPY --from=build /passanger-transportation-configuration/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]





