package com.todocodeacademy.cart_service.service;

import com.todocodeacademy.cart_service.dto.CartDTO;
import com.todocodeacademy.cart_service.repository.ProductAPIClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductAPIClient apiClient;

    @Override
    @Transactional
    public void saveCart(CartDTO dto) {

    }

    @Override
    @Transactional
    public void deleteCart(Long cartID) {

    }

    @Override
    public CartDTO getCartInfoByID(Long cartID) {

        return null;
    }

    @Override
    public List<CartDTO> getAllCarts() {

        return List.of();
    }
}
