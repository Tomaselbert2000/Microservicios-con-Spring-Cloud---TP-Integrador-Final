FROM openjdk:25-ea-1-jdk-slim
ARG JAR_FILE=target/eureka-server-0.0.1.jar
COPY ${JAR_FILE} eureka_server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]