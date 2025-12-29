package com.prueba.inventory.service; 

import com.prueba.inventory.api.dto.PurchaseRequest;
import com.prueba.inventory.api.dto.PurchaseResponse;
import com.prueba.inventory.client.ProductsClient;
import com.prueba.inventory.domain.Inventory;
import com.prueba.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private final InventoryRepository inventoryRepository;
    private final ProductsClient productsClient;

    public PurchaseService(
            InventoryRepository inventoryRepository,
            ProductsClient productsClient
    ) {
        this.inventoryRepository = inventoryRepository;
        this.productsClient = productsClient;
    }

    @Transactional
    public PurchaseResponse purchase(PurchaseRequest request) {

        var product = productsClient.getProductById(request.productId());

        Inventory inventory = inventoryRepository.findByProductId(request.productId())
                .orElseThrow(() -> new IllegalStateException("Inventario no existe"));

        if (inventory.getQuantity() < request.quantity()) {
            throw new IllegalStateException("Inventario insuficiente");
        }

        inventory.setQuantity(inventory.getQuantity() - request.quantity());
        inventoryRepository.save(inventory);

        BigDecimal total = product.data().attributes().price()
                .multiply(BigDecimal.valueOf(request.quantity()));

        return new PurchaseResponse(
                request.productId(),
                product.data().attributes().name(),
                request.quantity(),
                product.data().attributes().price(),
                total,
                LocalDateTime.now()
        );
    }
}
