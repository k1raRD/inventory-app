package com.k1rard.inventory.inventory.app.controllers;

import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import com.k1rard.inventory.inventory.app.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {

    @Autowired
    private ICategoryService service;

    /**
     * Get all categories.
     * @return {@link ResponseEntity} with metadata and categories data.
     */
    @GetMapping
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        return service.search();
    }

    /**
     * Get categories by id
     * @param id of the category to get
     * @return {@link ResponseEntity} with metadata and categories data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoryById(@PathVariable("id") Long id) {
        return service.searchById(id);
    }


}
