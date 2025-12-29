package com.prueba.products.api.dto;

public record JsonApiResponse<T>(
        JsonApiData<T> data
) {}
