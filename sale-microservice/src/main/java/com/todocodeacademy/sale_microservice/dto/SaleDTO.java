package com.todocodeacademy.sale_microservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class SaleDTO {

    private Long saleID;
    private Long cartID;
    private LocalDateTime timestamp;
    private BigDecimal saleTotal;
    private List<String> productNameList;
}
