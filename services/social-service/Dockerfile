FROM maven:3.8.5-openjdk-18-slim

WORKDIR /app

COPY . .

RUN mvn clean compile assembly:single -pl social-service -am

CMD cd social-service && java -jar /app/social-service/target/social-service-1.0-SNAPSHOT-jar-with-dependencies.jar