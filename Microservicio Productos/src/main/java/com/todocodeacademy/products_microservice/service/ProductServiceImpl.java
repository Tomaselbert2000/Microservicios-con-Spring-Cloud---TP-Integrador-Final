package com.todocodeacademy.products_microservice.service;

import com.todocodeacademy.products_microservice.dto.ProductDTO;
import com.todocodeacademy.products_microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;


    @Override
    public void createProduct(ProductDTO dto) {

    }

    @Override
    public void deleteProduct(String productName) {

    }

    @Override
    public ProductDTO getProductByName(String productName) {
        return null;
    }

    @Override
    public List<ProductDTO> getAllProducts() {

        return List.of();
    }
}
