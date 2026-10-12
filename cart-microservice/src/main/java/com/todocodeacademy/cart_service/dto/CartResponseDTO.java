package com.todocodeacademy.cart_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class CartResponseDTO {

    private Long cartID;
    private List<ProductDTO> products;
    private BigDecimal total;
}
