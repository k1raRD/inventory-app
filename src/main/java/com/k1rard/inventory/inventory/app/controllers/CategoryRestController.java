package com.k1rard.inventory.inventory.app.controllers;

import com.k1rard.inventory.inventory.app.models.Category;
import com.k1rard.inventory.inventory.app.response.CategoryResponseRest;
import com.k1rard.inventory.inventory.app.services.ICategoryService;
import com.k1rard.inventory.inventory.app.utils.CategoryExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {

    @Autowired
    private ICategoryService service;

    /**
     * Get all categories.
     *
     * @return {@link ResponseEntity} with metadata and categories data.
     */
    @GetMapping
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        return service.search();
    }

    /**
     * Get categories by id
     *
     * @param id of the category to get
     * @return {@link ResponseEntity} with metadata and categories data.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoryById(@PathVariable("id") Long id) {
        return service.searchById(id);
    }


    /**
     * Saved a category.
     *
     * @param category to save.
     * @return category saved on db.
     */
    @PostMapping
    public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category category) {
        return service.save(category);
    }

    /**
     * Update a category.
     *
     * @param category to update.
     * @param id       of the category to update.
     * @return {@link ResponseEntity} with metadata and categories data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category category, @PathVariable("id") Long id) {
        return service.update(category, id);
    }

    /**
     * delete category
     *
     * @param id of the category to delete
     * @return {@link ResponseEntity} with metadata
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseRest> saveCategory(@PathVariable("id") Long id) {
        return service.deleteById(id);
    }

    /**
     * Export to excel file
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_categories.xls";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<CategoryResponseRest> categoryResponse = service.search();

        CategoryExcelExporter excelExporter = new CategoryExcelExporter(categoryResponse.getBody()
                .getCategoryResponse().getCategories());

        excelExporter.export(response);
    }
}
