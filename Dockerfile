FROM openjdk:8-jdk-alpine
ADD ./base-scaffold-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","app.jar"]
