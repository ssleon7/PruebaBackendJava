package com.prueba.inventory.api;

import com.prueba.inventory.api.dto.JsonApiData;
import com.prueba.inventory.api.dto.JsonApiResponse;
import com.prueba.inventory.api.dto.PurchaseRequest;
import com.prueba.inventory.api.dto.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.prueba.inventory.service.PurchaseService;


@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping
    public JsonApiResponse<PurchaseResponse> purchase(
            @Valid @RequestBody PurchaseRequest request
    ) {
        PurchaseResponse response = service.purchase(request);

        return new JsonApiResponse<>(
                new JsonApiData<>("purchases", null, response)
        );
    }
}
