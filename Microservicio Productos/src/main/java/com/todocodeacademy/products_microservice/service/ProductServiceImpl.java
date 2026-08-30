package com.todocodeacademy.products_microservice.service;

import com.todocodeacademy.products_microservice.dto.ProductDTO;
import com.todocodeacademy.products_microservice.mapper.interfaces.ProductMapper;
import com.todocodeacademy.products_microservice.model.Product;
import com.todocodeacademy.products_microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Transactional
    public void createProduct(ProductDTO dto) {

        Product product = mapper.mapDTOtoEntity(dto);

        repository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(String productName) {

        Product product = repository.findProductByName(productName);

        if (product != null) {

            repository.delete(product);
        }
    }

    @Override
    @Transactional
    public void updateProduct(String productName, ProductDTO dto) {

        Product product = repository.findProductByName(productName);

        if (product != null) {

            updateProduct(product, dto);

            repository.save(product);
        }
    }

    @Override
    public ProductDTO getProductByName(String productName) {

        Product product = repository.findProductByName(productName);

        if (product != null) {

            return mapper.mapEntityToDTO(product);
        }

        return null;
    }

    @Override
    public List<ProductDTO> getAllProducts() {

        List<Product> products = repository.findAll();

        if (!products.isEmpty()) return products.stream().map(mapper::mapEntityToDTO).collect(Collectors.toList());

        return List.of();
    }

    private void updateProduct(Product entity, ProductDTO dto) {

        entity.setName(dto.getName());
        entity.setBrand(dto.getBrand());
        entity.setUnitPrice(dto.getUnitPrice());
    }
}
