FROM openjdk:25-jdk-slim
ARG JAR_FILE=target/homework4mongodb-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
# Expose the port the app runs on
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/app.jar"]
