package com.todocodeacademy.products_microservice.service;

import com.todocodeacademy.products_microservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    void createProduct(ProductDTO dto);

    void deleteProduct(String productName);

    void updateProduct(String productName, ProductDTO dto);

    ProductDTO getProductByName(String productName);

    List<ProductDTO> getAllProducts();
}
