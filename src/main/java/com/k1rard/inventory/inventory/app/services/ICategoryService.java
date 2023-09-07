package com.k1rard.inventory.inventory.app.services;

import com.k1rard.inventory.inventory.app.models.Category;
import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {

    ResponseEntity<CategoryResponseRest> search();
    ResponseEntity<CategoryResponseRest> searchById(Long id);
    ResponseEntity<CategoryResponseRest> save(Category category);
    ResponseEntity<CategoryResponseRest> update(Category category, Long id);
}
