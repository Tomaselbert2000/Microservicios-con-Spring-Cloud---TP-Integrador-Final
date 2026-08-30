package com.todocodeacademy.products_microservice.repository;

import com.todocodeacademy.products_microservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
