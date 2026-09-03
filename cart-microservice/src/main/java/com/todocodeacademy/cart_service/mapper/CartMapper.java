package com.todocodeacademy.cart_service.mapper;

import com.todocodeacademy.cart_service.dto.CartDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;
import com.todocodeacademy.cart_service.model.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.todocodeacademy.cart_service.mapper.MapperHelper.checkIfMapperInputIsNull;

@Component
public class CartMapper {

    public Cart mapDTOtoEntity(CartDTO dto) {

        checkIfMapperInputIsNull(dto);

        BigDecimal total = new BigDecimal("0");

        for (ProductDTO product : dto.getProducts()) {

            total = total.add(product.getUnitPrice());
        }

        return Cart.builder()
                .products(dto.getProducts())
                .total(total)
                .build();
    }

    public CartDTO mapEntityToDTO(Cart cart) {

        return CartDTO.builder()
                .cartID(cart.getCartID())
                .products(cart.getProducts())
                .total(cart.getTotal())
                .build();
    }
}
