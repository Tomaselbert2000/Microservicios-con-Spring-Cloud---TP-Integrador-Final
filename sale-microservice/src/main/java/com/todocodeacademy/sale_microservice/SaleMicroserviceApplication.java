package com.todocodeacademy.sale_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SaleMicroserviceApplication {

	static void main(String[] args) {

		SpringApplication.run(SaleMicroserviceApplication.class, args);
	}
}
