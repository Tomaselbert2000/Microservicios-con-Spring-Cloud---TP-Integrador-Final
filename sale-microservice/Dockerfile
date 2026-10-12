FROM openjdk:25-ea-1-jdk-slim
ARG JAR_FILE=target/sale-microservice-0.0.1.jar
COPY ${JAR_FILE} sale_microservice.jar
ENTRYPOINT ["java", "-jar", "sale_microservice.jar"]
EXPOSE 8020