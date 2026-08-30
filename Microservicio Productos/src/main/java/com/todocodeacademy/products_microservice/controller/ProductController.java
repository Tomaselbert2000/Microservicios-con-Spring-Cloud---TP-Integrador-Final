package com.todocodeacademy.products_microservice.controller;

import com.todocodeacademy.products_microservice.dto.ProductDTO;
import com.todocodeacademy.products_microservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.todocodeacademy.products_microservice.constant.StringResource.ControllerConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @GetMapping("/{name}")
    public ProductDTO getProductByName(@PathVariable String name) {

        return service.getProductByName(name);
    }

    @GetMapping("/all")
    public List<ProductDTO> getProductList() {

        return service.getAllProducts();
    }

    @PostMapping("/create")
    public String createProduct(@RequestBody ProductDTO dto) {

        service.createProduct(dto);

        return PRODUCT_CREATED_SUCCESSFULLY;
    }

    @DeleteMapping("/delete/{name}")
    public String deleteProduct(@PathVariable String name) {

        service.deleteProduct(name);

        return PRODUCT_DELETED_SUCCESSFULLY;
    }

    @PutMapping("/update/{name}")
    public String updateProduct(@PathVariable String name, @RequestBody ProductDTO dto) {

        service.updateProduct(name, dto);

        return PRODUCT_UPDATED_SUCCESSFULLY;
    }
}
