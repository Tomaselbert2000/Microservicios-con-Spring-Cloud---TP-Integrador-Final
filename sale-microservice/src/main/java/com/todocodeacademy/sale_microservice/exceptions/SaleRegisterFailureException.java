package com.todocodeacademy.sale_microservice.exceptions;

public class SaleRegisterFailureException extends RuntimeException {

    public SaleRegisterFailureException() {

        super("No fue posible registrar la venta. Intente nuevamente.");
    }
}
