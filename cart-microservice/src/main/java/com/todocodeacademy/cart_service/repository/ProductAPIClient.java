package com.todocodeacademy.cart_service.repository;

import com.todocodeacademy.cart_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-microservice")
public interface ProductAPIClient {

    @GetMapping("products/{id}")
    ProductDTO getProductByID(@PathVariable Long id);

    @GetMapping("/products/name/{name}")
    ProductDTO getProductInfoByName(@PathVariable String name);
}
