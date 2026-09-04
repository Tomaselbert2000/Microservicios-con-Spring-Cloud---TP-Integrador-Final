package com.todocodeacademy.sale_microservice.exceptions;

public class NullMapperInputException extends RuntimeException {

    public NullMapperInputException() {

        super("El objeto ingresado para mapeo es NULL.");
    }
}
