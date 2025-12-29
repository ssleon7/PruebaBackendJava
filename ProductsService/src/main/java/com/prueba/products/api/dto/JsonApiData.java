package com.prueba.products.api.dto;


public record JsonApiData<T>(
        String type,
        Long id,
        T attributes
) {}
