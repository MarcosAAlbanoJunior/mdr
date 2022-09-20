FROM maven:3.8.6-openjdk-11 AS build
COPY ./src /build/src
COPY pom.xml /build
WORKDIR /build
RUN mvn clean package

FROM openjdk:11.0-jdk-slim
COPY --from=build /build/target/rural-0.0.1-SNAPSHOT.jar /app/rural.jar
ENTRYPOINT ["java","-jar","/app/rural.jar"]
EXPOSE 8080