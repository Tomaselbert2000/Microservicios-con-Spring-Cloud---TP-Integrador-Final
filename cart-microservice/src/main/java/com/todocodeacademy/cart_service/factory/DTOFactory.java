package com.todocodeacademy.cart_service.factory;

import com.todocodeacademy.cart_service.dto.ProductDTO;

import java.math.BigDecimal;

public final class DTOFactory {

    private DTOFactory() {
    }

    public static ProductDTO errorDTO(String name, Throwable throwable) {

        return ProductDTO.builder()
                .productName("Ocurrió un error al obtener la información del producto: " + name + ": " + throwable.getMessage())
                .brand("----")
                .unitPrice(BigDecimal.valueOf(0))
                .build();
    }

    public static ProductDTO errorDTO(Long id, Throwable throwable) {

        return ProductDTO.builder()
                .productName("Ocurrió un error al obtener la información del producto: " + id + ": " + throwable.getMessage())
                .brand("----")
                .unitPrice(BigDecimal.valueOf(0))
                .build();
    }
}
