package com.k1rard.inventory.inventory.app.services;

import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {

    ResponseEntity<CategoryResponseRest> search();
}
