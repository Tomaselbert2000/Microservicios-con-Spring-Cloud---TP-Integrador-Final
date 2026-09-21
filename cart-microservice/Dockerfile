FROM openjdk:25-ea-1-jdk-slim
ARG JAR_FILE=target/cart-microservice-0.0.1.jar
COPY ${JAR_FILE} cart_microservice.jar
ENTRYPOINT ["java", "-jar", "cart_microservice.jar"]
EXPOSE 8010