package com.todocodeacademy.sale_microservice.mapper;

import com.todocodeacademy.sale_microservice.exceptions.NullMapperInputException;

public final class MapperHelper {

    public static void checkIfMapperInputIsNull(Object... objects) {

        for (Object object : objects) {

            if (object == null) {

                throw new NullMapperInputException();
            }
        }
    }
}
