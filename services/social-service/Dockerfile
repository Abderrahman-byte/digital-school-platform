FROM maven:3.8.5-openjdk-18-slim as build

WORKDIR /tmp/build

COPY pom.xml .
ADD common ./common
ADD authentication-service ./authentication-service
ADD social-service ./social-service
ADD school-management ./school-management

RUN mvn clean install -pl social-service -am

FROM openjdk:18

WORKDIR /app

COPY --from=build /tmp/build/social-service/target/socialservice-1.0-SNAPSHOT.jar .

CMD ["java", "-jar", "socialservice-1.0-SNAPSHOT.jar"]