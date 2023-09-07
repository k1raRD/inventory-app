package com.k1rard.inventory.inventory.app.services;

import com.k1rard.inventory.inventory.app.models.Category;
import com.k1rard.inventory.inventory.app.repositories.ICategoryRepository;
import com.k1rard.inventory.inventory.app.response.CategoryResponse;
import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private ICategoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {

        CategoryResponseRest response = new CategoryResponseRest();

        try {
            List<Category> categories = repository.findAll();
            response.getCategoryResponse().setCategories(categories);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");

        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
