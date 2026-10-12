package com.todocodeacademy.cart_service.mapper;

import com.todocodeacademy.cart_service.dto.CartResponseDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;
import com.todocodeacademy.cart_service.model.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.todocodeacademy.cart_service.mapper.MapperHelper.checkIfMapperInputIsNull;

@Component
public class CartMapper {

    public Cart mapDTOtoEntity(List<Long> productIDs, BigDecimal total) {

        checkIfMapperInputIsNull(productIDs, total);

        return Cart.builder()
                .products(productIDs)
                .total(total)
                .build();
    }

    public CartResponseDTO mapEntityToDTO(Cart cart, List<ProductDTO> products) {

        return CartResponseDTO.builder()
                .cartID(cart.getCartID())
                .products(products)
                .total(cart.getTotal())
                .build();
    }
}
