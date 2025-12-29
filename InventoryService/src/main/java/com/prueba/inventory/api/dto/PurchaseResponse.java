package com.prueba.inventory.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal total,
        LocalDateTime timestamp
) {}
