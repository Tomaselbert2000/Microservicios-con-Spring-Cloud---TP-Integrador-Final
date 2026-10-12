package com.todocodeacademy.cart_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {

        super("No se encontraron carritos de compra asociados al ID proporcionado.");
    }
}
