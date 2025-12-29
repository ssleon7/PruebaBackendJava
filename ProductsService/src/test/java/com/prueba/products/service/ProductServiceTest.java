package com.prueba.products.service;

import com.prueba.products.domain.Product;
import com.prueba.products.repository.ProductRepository;
import com.prueba.products.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void createProduct_ok() {
        Product product = new Product(null, "Computador portatil", BigDecimal.valueOf(500), null);

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product saved = invocation.getArgument(0);
                    saved.setId(1L);
                    return saved;
                });

        Product result = service.create(product);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Computador portatil", result.getName());
        assertEquals(BigDecimal.valueOf(500), result.getPrice());

        verify(repository).save(any(Product.class));
    }

    @Test
    void findById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.findById(99L));
    }
}
