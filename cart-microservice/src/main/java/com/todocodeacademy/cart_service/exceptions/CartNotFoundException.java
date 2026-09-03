package com.todocodeacademy.cart_service.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {

        super("No se encontraron carritos de compra asociados al ID proporcionado.");
    }
}
