package com.prueba.inventory.api.dto;

public record JsonApiData<T>(
        String type,
        Long id,
        T attributes
) {}
