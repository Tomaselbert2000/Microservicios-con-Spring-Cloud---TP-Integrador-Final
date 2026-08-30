package com.todocodeacademy.products_microservice.mapper.implementation;

import com.todocodeacademy.products_microservice.dto.ProductDTO;
import com.todocodeacademy.products_microservice.mapper.interfaces.ProductMapper;
import com.todocodeacademy.products_microservice.model.Product;
import org.springframework.stereotype.Component;

import static com.todocodeacademy.products_microservice.mapper.helper.MapperHelper.checkIfMapperInputIsNull;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product mapDTOtoEntity(ProductDTO dto) {

        checkIfMapperInputIsNull(dto);

        return Product.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .unitPrice(dto.getUnitPrice())
                .build();
    }

    @Override
    public ProductDTO mapEntityToDTO(Product entity) {

        checkIfMapperInputIsNull(entity);

        return ProductDTO.builder()
                .name(entity.getName())
                .brand(entity.getBrand())
                .unitPrice(entity.getUnitPrice())
                .build();
    }
}
