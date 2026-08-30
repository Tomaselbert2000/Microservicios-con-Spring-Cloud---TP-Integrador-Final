package com.todocodeacademy.products_microservice.mapper.interfaces;

import com.todocodeacademy.products_microservice.dto.ProductDTO;
import com.todocodeacademy.products_microservice.model.Product;

public interface ProductMapper {

    Product mapDTOtoEntity(ProductDTO dto);

    ProductDTO mapEntityToDTO(Product entity);
}
