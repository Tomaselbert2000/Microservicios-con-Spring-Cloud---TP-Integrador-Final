FROM openjdk:25-ea-1-jdk-slim
ARG JAR_FILE=target/products-microservice-0.0.1.jar
COPY ${JAR_FILE} products_microservice.jar
ENTRYPOINT ["java", "-jar", "products_microservice.jar"]
EXPOSE 8000