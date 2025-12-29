package com.prueba.products.api;

import com.prueba.products.api.dto.JsonApiData;
import com.prueba.products.api.dto.JsonApiResponse;
import com.prueba.products.domain.Product;
import com.prueba.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<JsonApiResponse<Product>> create(
            @Valid @RequestBody Product product
    ) {
        Product saved = service.create(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JsonApiResponse<>(
                        new JsonApiData<>("products", saved.getId(), saved)
                ));
    }

    @GetMapping("/{id}")
    public JsonApiResponse<Product> getById(@PathVariable Long id) {
        Product product = service.findById(id);

        return new JsonApiResponse<>(
                new JsonApiData<>("products", product.getId(), product)
        );
    }
}
