package com.todocodeacademy.cart_service.controller;

import com.todocodeacademy.cart_service.dto.CartDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;
import com.todocodeacademy.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.todocodeacademy.cart_service.constants.StringResource.ControllerConstants.CART_CREATED_SUCCESSFULLY;
import static com.todocodeacademy.cart_service.constants.StringResource.ControllerConstants.CART_DELETED_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService service;

    @GetMapping("/all")
    public List<CartDTO> getAllCarts() {

        return service.getAllCarts();
    }

    @GetMapping("/{cartID}")
    public CartDTO getCartInfoByID(@PathVariable Long cartID) {

        return service.getCartInfoByID(cartID);
    }

    @GetMapping("/products/{name}")
    public ProductDTO getProductInfoByName(@PathVariable String name) {

        return service.getProductByName(name);
    }

    @PostMapping("/create")
    public String createCart(@RequestBody CartDTO dto) {

        service.saveCart(dto);

        return CART_CREATED_SUCCESSFULLY;
    }

    @DeleteMapping("/delete/{cartID}")
    public String deleteCart(@PathVariable Long cartID) {

        service.deleteCart(cartID);

        return CART_DELETED_SUCCESSFULLY;
    }
}
