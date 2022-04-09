FROM maven:3.8.5-openjdk-18-slim

WORKDIR /app

COPY . .

RUN mvn clean compile assembly:single -pl authentication-service -am

CMD cd authentication-service && java -jar /app/authentication-service/target/authentication-service-1.0-SNAPSHOT-jar-with-dependencies.jar