package com.todocodeacademy.cart_service.service;

import com.todocodeacademy.cart_service.dto.CartRequestDTO;
import com.todocodeacademy.cart_service.dto.CartResponseDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;

import java.util.List;

public interface CartService {

    void saveCart(CartRequestDTO dto);

    void deleteCart(Long cartID);

    CartResponseDTO getCartInfoByID(Long cartID);

    List<CartResponseDTO> getAllCarts();

    ProductDTO getProductByName(String productName);
}
