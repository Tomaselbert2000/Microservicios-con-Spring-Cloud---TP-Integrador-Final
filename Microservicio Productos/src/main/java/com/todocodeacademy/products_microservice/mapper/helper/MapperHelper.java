package com.todocodeacademy.products_microservice.mapper.helper;

import com.todocodeacademy.products_microservice.exception.NullMapperInputException;

public final class MapperHelper {

    public static void checkIfMapperInputIsNull(Object... objects) {

        for (Object object : objects) {

            if (object == null) throw new NullMapperInputException();
        }
    }
}
