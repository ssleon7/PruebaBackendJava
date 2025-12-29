package com.prueba.inventory.client;

import com.prueba.inventory.client.dto.ProductResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class ProductsClient {

    private final RestClient restClient;
    private final String apiKey;

    public ProductsClient(
            @Value("${products.service.url}") String baseUrl,
            @Value("${products.api.key}") String apiKey) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000); 
        factory.setReadTimeout(2000); 

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
        this.apiKey = apiKey;
    }

    public ProductResponse getProductById(Long productId) {
        int attempts = 0;

        while (attempts < 2) {
            try {
                return restClient.get()
                        .uri("/api/v1/products/{id}", productId)
                        .header("X-API-KEY", apiKey)
                        .retrieve()
                        .body(ProductResponse.class);

            } catch (HttpClientErrorException.NotFound ex) {
                throw new EntityNotFoundException("Producto no existe");

            } catch (Exception ex) {
                attempts++;
                if (attempts >= 2) {
                    throw new IllegalStateException(
                            "No fue posible consultar ProductsService");
                }
            }
        }
        throw new IllegalStateException("Error inesperado");
    }
}
