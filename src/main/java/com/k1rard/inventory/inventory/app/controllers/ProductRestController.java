package com.k1rard.inventory.inventory.app.controllers;

import com.k1rard.inventory.inventory.app.models.Product;
import com.k1rard.inventory.inventory.app.response.ProductResponseRest;
import com.k1rard.inventory.inventory.app.services.IProductService;
import com.k1rard.inventory.inventory.app.utils.Util;
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

    /**
     *
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
                                                           @RequestParam("name") String name,
                                                           @RequestParam("price") Integer price,
                                                           @RequestParam("quantity") Integer quantity,
                                                           @RequestParam("categoryId") Long categoryId) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = productService.save(product, categoryId);
        return response;
    }
}
