package com.todocodeacademy.cart_service.mapper;

import com.todocodeacademy.cart_service.exceptions.NullMapperInputException;

public final class MapperHelper {

    public static void checkIfMapperInputIsNull(Object... objects){

        for(Object object : objects){

            if(object == null) throw new NullMapperInputException();
        }
    }
}
