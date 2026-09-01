package com.todocodeacademy.cart_service.service;

import com.todocodeacademy.cart_service.dto.CartDTO;

import java.util.List;

public interface CartService {

    void saveCart(CartDTO dto);

    void deleteCart(Long cartID);

    CartDTO getCartInfoByID(Long cartID);

    List<CartDTO> getAllCarts();
}
