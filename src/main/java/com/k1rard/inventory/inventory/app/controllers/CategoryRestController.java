package com.k1rard.inventory.inventory.app.controllers;

import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import com.k1rard.inventory.inventory.app.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {

    @Autowired
    private ICategoryService service;

    @GetMapping
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        ResponseEntity<CategoryResponseRest> response =  service.search();
        return response;
    }
}
