package com.todocodeacademy.products_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsMicroserviceApplication {

	static void main(String[] args) {

		SpringApplication.run(ProductsMicroserviceApplication.class, args);
	}
}