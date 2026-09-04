package com.todocodeacademy.sale_microservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private String productName;
    private String brand;
    private BigDecimal unitPrice;
}