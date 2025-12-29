package com.prueba.inventory.client.dto;

import java.math.BigDecimal;


public record ProductAttributes(
        String name,
        BigDecimal price,
        String description
) {}