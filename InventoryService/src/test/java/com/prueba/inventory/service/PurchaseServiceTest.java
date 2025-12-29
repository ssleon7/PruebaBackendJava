package com.prueba.inventory.service;

import com.prueba.inventory.client.ProductsClient;
import com.prueba.inventory.domain.Inventory;
import com.prueba.inventory.repository.InventoryRepository;
import com.prueba.inventory.api.dto.PurchaseRequest;
import com.prueba.inventory.api.dto.PurchaseResponse;
import com.prueba.inventory.client.dto.ProductAttributes;
import com.prueba.inventory.client.dto.ProductResponse;
import com.prueba.inventory.api.dto.JsonApiData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

        @Mock
        InventoryRepository inventoryRepository;

        @Mock
        ProductsClient productsClient;

        @InjectMocks
        PurchaseService service;

        @Test
        void purchase_ok() {
                Inventory inv = new Inventory(1L, 10L, 5);

                when(productsClient.getProductById(10L))
                                .thenReturn(mockProduct());

                when(inventoryRepository.findByProductId(10L))
                                .thenReturn(Optional.of(inv));

                PurchaseResponse response = service.purchase(
                                new PurchaseRequest(10L, 2));

                assertEquals(3, inv.getQuantity());
                assertNotNull(response);
        }

        private ProductResponse mockProduct() {
                return new ProductResponse(
                                new JsonApiData<>(
                                                "products",
                                                10L,
                                                new ProductAttributes(
                                                                "Portatil",
                                                                new BigDecimal("300"),
                                                                "Computador portatil")));
        }

}
