package com.todocodeacademy.cart_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class CartRequestDTO {

    private List<Long> productIDsList;
}
