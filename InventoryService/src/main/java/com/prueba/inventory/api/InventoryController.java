package com.prueba.inventory.api;

import com.prueba.inventory.api.dto.InventoryStatusResponse;
import com.prueba.inventory.client.ProductsClient;
import com.prueba.inventory.domain.Inventory;
import com.prueba.inventory.repository.InventoryRepository;


import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryRepository repository;
    private final ProductsClient productsClient;

    public InventoryController(InventoryRepository repository, ProductsClient productsClient) {
        this.repository = repository;
        this.productsClient = productsClient;
    }

    @PutMapping("/{productId}")
    public void setInventory(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        Inventory inventory = repository.findByProductId(productId)
                .orElse(new Inventory(null, productId, 0));

        inventory.setQuantity(quantity);
        repository.save(inventory);
    }

    @GetMapping("/{productId}")
    public InventoryStatusResponse getInventoryStatus(
            @PathVariable Long productId) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no existe"));

        var product = productsClient.getProductById(productId);

        return new InventoryStatusResponse(
                productId,
                product.data().attributes().name(),
                product.data().attributes().price(),
                inventory.getQuantity());
    }

}
