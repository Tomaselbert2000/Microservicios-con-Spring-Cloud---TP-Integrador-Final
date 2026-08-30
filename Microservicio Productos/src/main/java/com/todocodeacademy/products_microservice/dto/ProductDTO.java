package com.todocodeacademy.products_microservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class ProductDTO {

    private String name;
    private String brand;
    private BigDecimal unitPrice;
}
