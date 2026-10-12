package com.todocodeacademy.cart_service.exceptions;

public class NullMapperInputException extends RuntimeException {

    public NullMapperInputException() {

        super("El objeto ingresado para mapeo es NULL.");
    }
}
