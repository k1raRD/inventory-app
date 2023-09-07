package com.k1rard.inventory.inventory.app.services;

import com.k1rard.inventory.inventory.app.models.Category;
import com.k1rard.inventory.inventory.app.repositories.ICategoryRepository;
import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Optional<Category> category = repository.findById(id);
            if (category.isPresent()) {
                list.add(category.get());
                response.getCategoryResponse().setCategories(list);
                response.setMetadata("Respuesta ok", "00", "Categoria encontrada");
            } else {
                response.setMetadata("Respuesta no ok", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al consultar por id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Category categorySaved = repository.save(category);

            if (categorySaved != null) {
                list.add(categorySaved);
                response.getCategoryResponse().setCategories(list);
                response.setMetadata("Respuesta ok", "00", "Categoria guardada");

            } else {
                response.setMetadata("Respuesta no ok", "-1", "Categoria no fue guardada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al guardar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Optional<Category> categoryOptional = repository.findById(id);

            if (categoryOptional.isPresent()) {
                categoryOptional.get().setName(category.getName());
                categoryOptional.get().setDescription(category.getDescription());

                Category categoryToUpdate = repository.save(categoryOptional.get());

                if (categoryToUpdate != null) {
                    list.add(categoryToUpdate);
                    response.getCategoryResponse().setCategories(list);
                    response.setMetadata("Respuesta ok", "00", "Categoria actualizada");
                } else {
                    response.setMetadata("Respuesta no ok", "-1", "Categoria no actualizada");
                    return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta no ok", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al actualizar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
