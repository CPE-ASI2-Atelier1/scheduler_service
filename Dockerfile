# Build stage
FROM maven:3.9 AS build
WORKDIR /usr/app
COPY . .
RUN mvn install:install-file \
    -Dfile=./lib/shared-dto-1.0-SNAPSHOT.jar \
    -DgroupId=com.cpe.asi2.atelier1.dto \
    -DartifactId=shared-dto \
    -Dversion=1.0-SNAPSHOT \
    -Dpackaging=jar
RUN mvn clean package

# Package stage
FROM eclipse-temurin:21-alpine
WORKDIR /usr/app
ARG ACTIVEMQ_HOST=localhost
ENV ACTIVEMQ_HOST=${ACTIVEMQ_HOST}
COPY --from=build /usr/app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT java -jar app.jar