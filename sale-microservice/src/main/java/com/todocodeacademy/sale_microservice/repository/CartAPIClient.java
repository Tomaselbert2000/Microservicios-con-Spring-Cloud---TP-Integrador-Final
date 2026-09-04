package com.todocodeacademy.sale_microservice.repository;

import com.todocodeacademy.sale_microservice.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-microservice")
public interface CartAPIClient {

    @GetMapping("/carts/{cartID}")
    CartDTO getCartInfoByID(@PathVariable Long cartID);
}
