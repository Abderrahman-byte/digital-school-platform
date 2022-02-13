FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY . .

RUN mvn clean compile assembly:single -pl school-management -am

CMD cd school-management && java -jar /app/school-management/target/school-management-1.0-SNAPSHOT-jar-with-dependencies.jar