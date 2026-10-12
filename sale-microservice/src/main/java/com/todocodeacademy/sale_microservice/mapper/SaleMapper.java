package com.todocodeacademy.sale_microservice.mapper;

import com.todocodeacademy.sale_microservice.dto.CartDTO;
import com.todocodeacademy.sale_microservice.dto.ProductDTO;
import com.todocodeacademy.sale_microservice.dto.SaleDTO;
import com.todocodeacademy.sale_microservice.model.Sale;
import org.springframework.stereotype.Component;

import static com.todocodeacademy.sale_microservice.mapper.MapperHelper.checkIfMapperInputIsNull;

@Component
public class SaleMapper {

    public Sale mapDTOtoEntity(Long cartID) {

        checkIfMapperInputIsNull(cartID);

        return Sale.builder()
                .cartID(cartID)
                .build();
    }

    public SaleDTO mapEntityToDTO(Sale entity, CartDTO dto) {

        return SaleDTO.builder()
                .saleID(entity.getSaleID())
                .cartID(dto.getCartID())
                .timestamp(entity.getTimestamp())
                .saleTotal(dto.getTotal())
                .productNameList(dto.getProducts().stream().map(ProductDTO::getProductName).toList())
                .build();
    }
}
