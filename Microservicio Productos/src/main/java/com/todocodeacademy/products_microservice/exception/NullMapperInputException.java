package com.todocodeacademy.products_microservice.exception;

public class NullMapperInputException extends RuntimeException {

    public NullMapperInputException() {

        super("El objeto ingresado para mapeo es NULL.");
    }
}
