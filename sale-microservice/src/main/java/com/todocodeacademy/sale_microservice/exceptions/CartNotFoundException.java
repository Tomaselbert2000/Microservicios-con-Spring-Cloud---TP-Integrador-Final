package com.todocodeacademy.sale_microservice.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {

        super("No se encontraron carritos de compra con el ID proporcionado.");
    }
}
