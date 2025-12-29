package com.prueba.inventory.client.dto;

import com.prueba.inventory.api.dto.JsonApiData;

public record ProductResponse(
        JsonApiData<ProductAttributes> data
) {}