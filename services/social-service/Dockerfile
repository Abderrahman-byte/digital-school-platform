FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY . .

RUN mvn clean compile assembly:single -pl social-service -am

CMD cd social-service && java -jar /app/social-service/target/social-service-1.0-SNAPSHOT-jar-with-dependencies.jar