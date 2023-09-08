package com.k1rard.inventory.inventory.app.services;

import com.k1rard.inventory.inventory.app.models.Product;
import com.k1rard.inventory.inventory.app.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    ResponseEntity<ProductResponseRest> searchById(Long id);
    ResponseEntity<ProductResponseRest> searchByName(String name);
    ResponseEntity<ProductResponseRest> deleteById(Long id);
    ResponseEntity<ProductResponseRest> search();
}
