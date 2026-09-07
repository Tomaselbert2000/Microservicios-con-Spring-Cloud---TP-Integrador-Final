package com.todocodeacademy.cart_service.controller;

import com.todocodeacademy.cart_service.dto.CartRequestDTO;
import com.todocodeacademy.cart_service.dto.CartResponseDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;
import com.todocodeacademy.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.todocodeacademy.cart_service.constants.StringResource.ControllerConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService service;

    @GetMapping("/all")
    public List<CartResponseDTO> getAllCarts() {

        return service.getAllCarts();
    }

    @GetMapping("/{cartID}")
    public CartResponseDTO getCartInfoByID(@PathVariable Long cartID) {

        return service.getCartInfoByID(cartID);
    }

    @GetMapping("/products/{name}")
    public ProductDTO getProductInfoByName(@PathVariable String name) {

        return service.getProductByName(name);
    }

    @PostMapping("/create")
    public String createCart(@RequestBody CartRequestDTO dto) {

        service.saveCart(dto);

        return CART_CREATED_SUCCESSFULLY;
    }

    @DeleteMapping("/delete/{cartID}")
    public String deleteCart(@PathVariable Long cartID) {

        service.deleteCart(cartID);

        return CART_DELETED_SUCCESSFULLY;
    }

    @PostMapping("/{cartID}/products/{productID}")
    public String addProductToCart(@PathVariable Long cartID, @PathVariable Long productID) {

        service.addProductToCart(cartID, productID);

        return PRODUCT_ADDED_SUCCESSFULLY;
    }

    @DeleteMapping("/{cartID}/products/{productID}")
    public String removeProductFromCart(@PathVariable Long cartID, @PathVariable Long productID) {

        service.removeProductFromCart(cartID, productID);

        return PRODUCT_REMOVED_SUCCESSFULLY;
    }
}
