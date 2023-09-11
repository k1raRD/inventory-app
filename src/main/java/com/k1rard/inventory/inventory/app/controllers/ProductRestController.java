package com.k1rard.inventory.inventory.app.controllers;

import com.k1rard.inventory.inventory.app.models.Product;
import com.k1rard.inventory.inventory.app.response.ProductResponseRest;
import com.k1rard.inventory.inventory.app.services.IProductService;
import com.k1rard.inventory.inventory.app.utils.ProductExcelExporter;
import com.k1rard.inventory.inventory.app.utils.Util;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<ProductResponseRest> search() {
        return productService.search();
    }

    /**
     * Search by name
     *
     * @param name
     * @return
     */
    @GetMapping("/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable("name") String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable("id") Long id) {
        return productService.searchById(id);
    }

    /**
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity<ProductResponseRest> saveProduct(@RequestParam("picture") MultipartFile picture,
                                                           @RequestParam("name") String name, @RequestParam("price") Integer price, @RequestParam("quantity") Integer quantity,
                                                           @RequestParam("categoryId") Long categoryId) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = productService.save(product, categoryId);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseRest> saveProduct(@PathVariable("id") Long id,
                                                           @RequestParam("picture") MultipartFile picture,
                                                           @RequestParam("name") String name, @RequestParam("price") Integer price, @RequestParam("quantity") Integer quantity,
                                                           @RequestParam("categoryId") Long categoryId) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        return productService.update(product, categoryId, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable("id") Long id) {
        return productService.deleteById(id);
    }

    /**
     * export products to excel
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_products";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<ProductResponseRest> productResponse = productService.search();

        ProductExcelExporter excelExporter = new ProductExcelExporter(productResponse.getBody()
                        .getProduct().getProducts());

        excelExporter.export(response);
    }
}
