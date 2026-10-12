package com.todocodeacademy.sale_microservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class ProductDTO {

    private String productName;
    private String brand;
    private BigDecimal unitPrice;
}