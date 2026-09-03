package com.todocodeacademy.cart_service.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {

        super("No se encontraron productos con el nombre ingresado");
    }
}
