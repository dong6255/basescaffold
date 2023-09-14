FROM openjdk:8-jdk-alpine
ADD ./target/base-scaffold-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","app.jar"]
