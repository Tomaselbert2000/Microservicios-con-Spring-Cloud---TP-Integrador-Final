package com.todocodeacademy.cart_service.repository;

import com.todocodeacademy.cart_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-api-client")
public interface ProductAPIClient {

    @GetMapping("/products/{name}")
    ProductDTO getProductInfoByName(@PathVariable String name);
}
