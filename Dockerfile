FROM openjdk:8
ADD ./target/base-scaffold-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
