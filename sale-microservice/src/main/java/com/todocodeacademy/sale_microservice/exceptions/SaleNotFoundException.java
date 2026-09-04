package com.todocodeacademy.sale_microservice.exceptions;

public class SaleNotFoundException extends RuntimeException {

    public SaleNotFoundException() {

        super("No se encontraron ventas con el ID proporcionado.");
    }
}
