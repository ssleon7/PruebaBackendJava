package com.prueba.inventory.api.dto;

import java.math.BigDecimal;

public record InventoryStatusResponse(
        Long productId,
        String productName,
        BigDecimal price,
        Integer availableQuantity
) {}
