package com.prueba.inventory.repository;

import com.prueba.inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);
    Optional<Inventory> getProductById(Long productId);
}
