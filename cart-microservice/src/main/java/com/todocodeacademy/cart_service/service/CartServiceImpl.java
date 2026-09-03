package com.todocodeacademy.cart_service.service;

import com.todocodeacademy.cart_service.dto.CartDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;
import com.todocodeacademy.cart_service.exceptions.CartNotFoundException;
import com.todocodeacademy.cart_service.exceptions.ProductNotFoundException;
import com.todocodeacademy.cart_service.mapper.CartMapper;
import com.todocodeacademy.cart_service.model.Cart;
import com.todocodeacademy.cart_service.repository.CartRepository;
import com.todocodeacademy.cart_service.repository.ProductAPIClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductAPIClient apiClient;
    private final CartRepository repository;
    private final CartMapper mapper;

    @Override
    @Transactional
    public void saveCart(CartDTO dto) {

        validateProductExistence(dto.getProducts());

        Cart cart = mapper.mapDTOtoEntity(dto);

        repository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCart(Long cartID) {

        Cart cart = repository.findById(cartID).orElseThrow(CartNotFoundException::new);

        repository.delete(cart);
    }

    @Override
    public CartDTO getCartInfoByID(Long cartID) {

        Cart cart = repository.findById(cartID).orElseThrow(CartNotFoundException::new);

        return mapper.mapEntityToDTO(cart);
    }

    @Override
    public List<CartDTO> getAllCarts() {

        List<Cart> carts = repository.findAll();

        if (!carts.isEmpty()) return carts.stream().map(mapper::mapEntityToDTO).collect(Collectors.toList());

        return List.of();
    }

    @Override
    public ProductDTO getProductByName(String productName) {

        return apiClient.getProductInfoByName(productName);
    }

    private void validateProductExistence(List<ProductDTO> products) {

        if (!products.isEmpty()) {

            for (ProductDTO product : products) {

                if (apiClient.getProductInfoByName(product.getProductName()) == null)
                    throw new ProductNotFoundException();
            }
        }
    }
}
