package com.prueba.inventory.api.dto;

public record JsonApiResponse<T>(
        JsonApiData<T> data
) {}
